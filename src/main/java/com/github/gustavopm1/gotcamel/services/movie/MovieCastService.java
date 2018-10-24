package com.github.gustavopm1.gotcamel.services.movie;

import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.movie.Movie;
import com.github.gustavopm1.gotcamel.models.movie.MovieCast;
import org.apache.camel.Body;
import org.apache.camel.Header;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static com.github.gustavopm1.gotcamel.GotCamelConstants.TYPE_VALUE;

@Service
public class MovieCastService {
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

    public Response<Movie> getMovieCastById(@Header(TYPE_VALUE) Integer id, @Body Response<Movie> movie){
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
