package com.github.gustavopm1.gotcamel.services.movie;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.movie.Crew;
import com.github.gustavopm1.gotcamel.models.movie.Movie;
import com.github.gustavopm1.gotcamel.services.AbstractRequestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Body;
import org.apache.camel.Header;
import org.apache.camel.Headers;
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
public class MovieCrewService extends AbstractRequestService {

    @Override
    public String getURL(Map<String, Object> headers) {
        return String.valueOf(headers.get(TYPE_VALUE)).concat("/credits");
    }

    @Override
    public Map<String, String> getHeaders(Map<String, Object> headers) {
        return new HashMap<>();
    }

    public Response<Movie> getMovieCrewById(@Header(TYPE_VALUE) String id, @Body Response<Movie> movie, @Headers Map<String,Object> headers){
        ResponseEntity<String> response = doGet(headers);
        if(response.getStatusCode().equals(HttpStatus.OK)){
            try {

                JsonNode json = new ObjectMapper().readTree(response.getBody());

                List<Crew> movieCrew = new ObjectMapper().readValue(json.get("crew").toString(), new TypeReference<List<Crew>>(){});
                movie.getBody().setCrew(movieCrew.stream().limit(2).collect(Collectors.toList()));
                return Response.<Movie>builder()
                        .found(true)
                        .body(movie.getBody())
                        .build();
            }catch (Exception e){
                log.error("Erro ao parsear crew!", e);
            }
        }
        return Response.<Movie>builder()
                .found(false)
                .build();
    }

    public Response<Movie> getMovieCrewByName(@Header(TYPE_VALUE) String movieName, @Body Response<Movie> movie){
        List<Crew> crew = Arrays.asList(Crew.builder().id(1090).name("John Mctiernan").department("Directing").build(),
                Crew.builder().id(19893).name("warren Lewis").department("Writing").build());

        movie.getBody().setCrew(crew);

        return movie;
    }


}
