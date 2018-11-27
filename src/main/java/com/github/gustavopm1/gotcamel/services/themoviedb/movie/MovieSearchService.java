package com.github.gustavopm1.gotcamel.services.themoviedb.movie;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.SearchType;
import com.github.gustavopm1.gotcamel.models.themoviedb.movie.Movie;
import com.github.gustavopm1.gotcamel.services.TheMovieDBAbstractRequestService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Header;
import org.apache.camel.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.gustavopm1.gotcamel.GotCamelConstants.TYPE_NAME;
import static com.github.gustavopm1.gotcamel.GotCamelConstants.TYPE_VALUE;

@Service
@Slf4j
public class MovieSearchService extends TheMovieDBAbstractRequestService {

    @Autowired
    @Setter
    private MovieSearchByIdService movieSearchByIdService;


    @Override
    public String getURL(Map<String, Object> headers) {
        return configuration.getServices().getMovieUrlName();
    }

    @Override
    public Map<String, String> getHeaders(Map<String, Object> headers) {
        return new HashMap<>();
    }

    public Map<String,String> getParams(Map<String, Object> params){
        Map<String,String> parameters = new HashMap<>();
        parameters.put("query",String.valueOf(params.get(TYPE_VALUE)));
        return parameters;
    }

    public Response<Movie> getMovie(@Header(TYPE_VALUE) String movieName, @Headers Map<String,Object> headers) {

            ResponseEntity<String> response = doGet(headers);

            if (response.getStatusCode().equals(HttpStatus.OK)) {
                try {

                    JsonNode json = new ObjectMapper().readTree(response.getBody());

                    List<Movie> movies = new ObjectMapper().readValue(json.get("results").toString(), new TypeReference<List<Movie>>() {
                    });

                    Response<Movie> responseMovie = Response.<Movie>builder()
                            .found(true)
                            .body(movies.get(0))
                            .build();


                    headers.put(TYPE_NAME, SearchType.MOVIEID);
                    headers.put(TYPE_VALUE, responseMovie.getBody().getId());

                    return movieSearchByIdService.getMovieById(Integer.toString(movies.get(0).getId()), headers);

                } catch (Exception e) {
                    log.error("Erro ao parsear filme!", e);
                }
            }

            return Response.<Movie>builder()
                    .found(false)
                    .build();


    }

}
