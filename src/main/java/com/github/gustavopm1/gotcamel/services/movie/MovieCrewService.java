package com.github.gustavopm1.gotcamel.services.movie;

import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.movie.Crew;
import com.github.gustavopm1.gotcamel.models.movie.Movie;
import org.apache.camel.Body;
import org.apache.camel.Header;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static com.github.gustavopm1.gotcamel.GotCamelConstants.TYPE_VALUE;

@Service
public class MovieCrewService {

    public Response<Movie> getMovieCrewById(@Header(TYPE_VALUE) String id, @Body Response<Movie> movie){

        Integer movieID = Integer.parseInt(id);

        List<Crew> crew = Arrays.asList(Crew.builder().id(1090).name("John Mctiernan").department("Directing").build(),
                Crew.builder().id(19893).name("warren Lewis").department("Writing").build());

        movie.getBody().setCrew(crew);
        return movie;
    }

    public Response<Movie> getMovieCrewByName(@Header(TYPE_VALUE) String movieName, @Body Response<Movie> movie){
        List<Crew> crew = Arrays.asList(Crew.builder().id(1090).name("John Mctiernan").department("Directing").build(),
                Crew.builder().id(19893).name("warren Lewis").department("Writing").build());

        movie.getBody().setCrew(crew);

        return movie;
    }

}
