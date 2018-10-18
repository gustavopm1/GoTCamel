package com.github.gustavopm1.gotcamel.services.movie;

import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.movie.Movie;
import org.apache.camel.Header;
import org.springframework.stereotype.Service;

@Service
public class MovieSearchService {

    public Response<Movie> getMovie(@Header("MOVIE_NAME") String movieName){
        return Response.<Movie>builder()
                .body(
                        Movie.builder()
                        .name("13th Warrior")
                        .year(1999)
                        .build()
                )
                .found(true)
                .build();
    }

}
