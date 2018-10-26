package com.github.gustavopm1.gotcamel.services.movie;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gustavopm1.gotcamel.configuration.GotCamelConfiguration;
import com.github.gustavopm1.gotcamel.configuration.GotCamelConfigurationServices;
import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.movie.Movie;
import com.github.gustavopm1.gotcamel.services.AbstractRequestService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Header;
import org.apache.camel.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.github.gustavopm1.gotcamel.GotCamelConstants.TYPE_VALUE;

@Service
@Slf4j
public class MovieSearchService extends AbstractRequestService {

    @Autowired
    @Setter
    private GotCamelConfiguration configuration;


    @Override
    public String getURL(Map<String, Object> headers) {
        return configuration.getServices().getMovieUrl().replace(":id",String.valueOf(headers.get(TYPE_VALUE)));
    }

    @Override
    public Map<String, String> getHeaders(Map<String, Object> headers) {
        return new HashMap<>();
    }

    public Response<Movie> getMovie(@Header(TYPE_VALUE) String movieName, @Headers Map<String,Object> headers){

        ResponseEntity<String> response = doGet(headers);

        if(response.getStatusCode().equals(HttpStatus.OK)){
            try {
                Movie movie=  new ObjectMapper().readValue(response.getBody(), Movie.class);
                return Response.<Movie>builder()
                        .found(true)
                        .body(movie)
                        .build();
            }catch (Exception e){
                log.error("Erro ao parsear filme!", e);
            }
        }

        return Response.<Movie>builder()
                .found(false)
                .build();
    }

    public Response<Movie> getMovieById(@Header (TYPE_VALUE) String id, @Headers Map<String, Object> headers){

        ResponseEntity<String> response = doGet(headers);
        System.out.println("Response::" + response.getBody());
        if(response.getStatusCode().equals(HttpStatus.OK)){
            try {
                Movie movie=  new ObjectMapper().readValue(response.getBody(), Movie.class);
                 return Response.<Movie>builder()
                        .found(true)
                        .body(movie)
                        .build();
            }catch (Exception e){
                log.error("Erro ao parsear filme!", e);
            }
        }

        return Response.<Movie>builder()
                .found(false)
                .build();

    }



}
