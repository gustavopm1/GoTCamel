package com.github.gustavopm1.gotcamel.services.movie;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gustavopm1.gotcamel.configuration.GotCamelConfiguration;
import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.movie.Movie;
import com.github.gustavopm1.gotcamel.models.movie.MovieKeyword;
import com.github.gustavopm1.gotcamel.services.AbstractRequestService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Body;
import org.apache.camel.Header;
import org.apache.camel.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.gustavopm1.gotcamel.GotCamelConstants.TYPE_VALUE;

@Service
@Slf4j
public class MovieKeywordsService extends AbstractRequestService {

    @Autowired
    @Setter
    private GotCamelConfiguration configuration;

    @Override
    public String getURL(Map<String, Object> headers) {
        return configuration.getServices().getMovieKeywords().replace(":id",String.valueOf(headers.get(TYPE_VALUE)));    }

    @Override
    public Map<String, String> getHeaders(Map<String, Object> headers) { return new HashMap<>(); }

    public Map<String, String> getParams(Map<String, Object> params) {
        return new HashMap<>();
    }


    public Response<Movie> getMovieKeywordsById(@Header(TYPE_VALUE) String id, @Body Response<Movie> movie, @Headers Map<String,Object> headers){

        ResponseEntity<String> response = doGet(headers);
        if(response.getStatusCode().equals(HttpStatus.OK)){
            try {

                JsonNode json = new ObjectMapper().readTree(response.getBody());

                List<MovieKeyword> movieKeywords = new ObjectMapper().readValue(json.get("keywords").toString(), new TypeReference<List<MovieKeyword>>(){});
                movie.getBody().setKeywords(movieKeywords.stream().limit(3).collect(Collectors.toList()));
                return Response.<Movie>builder()
                        .found(true)
                        .body(movie.getBody())
                        .build();
            }catch (Exception e){
                log.error("Erro ao parsear keywords!", e);
            }
        }
        return Response.<Movie>builder()
                .found(false)
                .build();
    }


    public Response<Movie> getMovieKeywordsByName(@Header(TYPE_VALUE) String movieName, @Body Response<Movie> movie){

        List<MovieKeyword> keywords = Arrays.asList(MovieKeyword.builder().id(616).name("witch").build(),
                MovieKeyword.builder().id(1585).name("snake").build(),
                MovieKeyword.builder().id(1964).name("cave").build());

        movie.getBody().setKeywords(keywords);
        return movie;
    }


}
