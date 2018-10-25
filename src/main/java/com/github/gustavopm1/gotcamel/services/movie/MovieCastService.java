package com.github.gustavopm1.gotcamel.services.movie;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.movie.Movie;
import com.github.gustavopm1.gotcamel.models.movie.MovieCast;
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
public class MovieCastService extends AbstractRequestService {

    @Override
    public String getURL(Map<String, Object> headers) {
        return String.valueOf(headers.get(TYPE_VALUE)).concat("/credits");
    }

    @Override
    public Map<String, String> getHeaders(Map<String, Object> headers) {
        return new HashMap<>();
    }

    public Response<List<MovieCast>> getMovieCast(@Header(TYPE_VALUE) String movieName){
        return Response.<List<MovieCast>>builder()
                .body(
                        Arrays.asList(
                                MovieCast.builder()
                                .id(6677)
                                .name("Kyle MacLachlan")
                                .character("Dale Cooper")
                                .profile_path("/7DnMuDlSdpycAQQxOIDmV66qerc.jpg")
                                .build(),
                                MovieCast.builder()
                                .id(6726)
                                .name("Sheryl Lee")
                                .character("Laura Palmer")
                                .profile_path("/6mUGpZGzDiYevpWLM7AjlTN8UgV.jpg")
                                .build(),
                                MovieCast.builder()
                                .id(6719)
                                .name("Ray Wise")
                                .character("Leland Palmer")
                                .profile_path("/z1EXC8gYfFddC010e9YK5kI5NKC.jpg")
                                .build()
                                )
                )
                .found(true)
                .build();
    }

    public Response<Movie> getMovieCastById(@Header(TYPE_VALUE) Integer id, @Body Response<Movie> movie,  @Headers Map<String,Object> headers){
        ResponseEntity<String> response = doGet(headers);
        if(response.getStatusCode().equals(HttpStatus.OK)){
            try {

                JsonNode json = new ObjectMapper().readTree(response.getBody());

                List<MovieCast> movieCast = new ObjectMapper().readValue(json.get("cast").toString(), new TypeReference<List<MovieCast>>(){});
                movie.getBody().setCast(movieCast.stream().limit(3).collect(Collectors.toList()));
                return Response.<Movie>builder()
                        .found(true)
                        .body(movie.getBody())
                        .build();
            }catch (Exception e){
                log.error("Erro ao parsear cast!", e);
            }
        }
        return Response.<Movie>builder()
                .found(false)
                .build();
    }

    public Response<Movie> getMovieCastByName(@Header(TYPE_VALUE) String movieName, @Body Response<Movie> movie){

        List<MovieCast> cast = Arrays.asList(MovieCast.builder()
                        .id(3131)
                        .name("Antonio Banderas")
                        .character("Ahmad Ibn Fadlān")
                        .profile_path("/85197jARsr06xQ84NhP9YoBL3sR.jpg")
                        .build(),
                MovieCast.builder()
                        .id(6200)
                        .name("Diane Venora")
                        .character("Queen Weilew")
                        .profile_path("/3k7w5Y7gTAxN1JML7jhN8F8rVrS.jpg")
                        .build(),
                MovieCast.builder()
                        .id(19899)
                        .name("Dennis Storhøi")
                        .character("Herger the Joyous")
                        .profile_path("/zc8O7O8l8wtX3EQmDV7jWOPjmHJ.jpg")
                        .build());


        movie.getBody().setCast(cast);

        return movie;
    }
}
