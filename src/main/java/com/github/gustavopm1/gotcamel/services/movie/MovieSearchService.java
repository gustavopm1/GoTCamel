package com.github.gustavopm1.gotcamel.services.movie;

import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.movie.*;
import org.apache.camel.Body;
import org.apache.camel.Header;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.gustavopm1.gotcamel.GotCamelConstants.MOVIE_ID;
import static com.github.gustavopm1.gotcamel.GotCamelConstants.MOVIE_NAME;
import static com.github.gustavopm1.gotcamel.GotCamelConstants.TYPE_VALUE;

@Service
public class MovieSearchService {

    public Response<Movie> getMovie(@Header(TYPE_VALUE) String movieName){
        return Response.<Movie>builder()
                .body(
                        Movie.builder()
                        .original_title("13th Warrior")
                        .year(1999)
                        .id(1911)
                        .overview("In AD 922, Arab courtier, Ahmad Ibn Fadlan accompanies a party of Vikings to the barbaric North to combat a terror " +
                                "that slaughters Vikings and devours their flesh.")
                        .genres(Arrays.asList(Genre.builder().id(28).name("Action").build(),
                                              Genre.builder().id(12).name("Adventure").build(),
                                              Genre.builder().id(14).name("Fantasy").build()))
                        .runtime(102)
                        .build()
                )
                .found(true)
                .build();
    }





}
