package com.github.gustavopm1.gotcamel.services.movie;

import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.movie.Movie;
import com.github.gustavopm1.gotcamel.models.movie.MovieKeyword;
import org.apache.camel.Body;
import org.apache.camel.Header;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static com.github.gustavopm1.gotcamel.GotCamelConstants.TYPE_VALUE;

@Service
public class MovieKeywordsService {

    public Response<Movie> getMovieKeywordsById(@Header(TYPE_VALUE) String id, @Body Response<Movie> movie){

        Integer movieID = Integer.parseInt(id);

        List<MovieKeyword> keywords = Arrays.asList(MovieKeyword.builder().id(616).name("witch").build(),
                MovieKeyword.builder().id(1585).name("snake").build(),
                MovieKeyword.builder().id(1964).name("cave").build());

        movie.getBody().setId(movieID);
        movie.getBody().setKeywords(keywords);


        return movie;
    }

    public Response<Movie> getMovieKeywordsByName(@Header(TYPE_VALUE) String movieName, @Body Response<Movie> movie){

        List<MovieKeyword> keywords = Arrays.asList(MovieKeyword.builder().id(616).name("witch").build(),
                MovieKeyword.builder().id(1585).name("snake").build(),
                MovieKeyword.builder().id(1964).name("cave").build());

        movie.getBody().setKeywords(keywords);
        return movie;
    }

}
