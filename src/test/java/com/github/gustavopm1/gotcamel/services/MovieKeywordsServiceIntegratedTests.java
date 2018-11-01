package com.github.gustavopm1.gotcamel.services;

import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.SearchType;
import com.github.gustavopm1.gotcamel.models.movie.Movie;
import com.github.gustavopm1.gotcamel.models.movie.MovieKeyword;
import com.github.gustavopm1.gotcamel.services.movie.MovieKeywordsService;
import com.github.gustavopm1.gotcamel.services.movie.MovieSearchByIdService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;

import static com.github.gustavopm1.gotcamel.GotCamelConstants.TYPE_NAME;
import static com.github.gustavopm1.gotcamel.GotCamelConstants.TYPE_VALUE;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class MovieKeywordsServiceIntegratedTests {

    @Autowired
    MovieKeywordsService movieKeywordsService;

    @Autowired
    MovieSearchByIdService movieSearchByIdService;

    @Test
    public void integratedTestGETMovieCrewById(){
        Movie movie = Movie.builder()
                .id(1911)
                .runtime(102)
                .original_title("The 13th Warrior")
                .overview("In AD 922, Arab courtier, Ahmad Ibn Fadlan accompanies a party of Vikings to the barbaric North to combat a terror that slaughters Vikings and devours their flesh.")
                .build();

        HashMap< String, Object> headers = new HashMap<>();

        headers.put(TYPE_NAME, SearchType.MOVIEID);
        headers.put(TYPE_VALUE, Integer.toString(movie.getId()));

        Response<Movie> response = movieSearchByIdService.getMovieById("1911", headers);

        headers.put(TYPE_NAME, SearchType.CASTMOVIEID);

        response = movieKeywordsService.getMovieKeywordsById(Integer.toString(response.getBody().getId()),response,headers);

        assertThat(response)
                .isNotNull()
                .hasFieldOrPropertyWithValue("found",true)
                .hasFieldOrProperty("body");

        assertThat(response.getBody())
                .isNotNull()
                .isInstanceOf(Movie.class)
                .hasFieldOrPropertyWithValue("original_title","The 13th Warrior")
                .hasFieldOrPropertyWithValue("keywords", Arrays.asList(MovieKeyword.builder().id(616).name("witch").build(),
                        MovieKeyword.builder().id(1585).name("snake").build(),
                        MovieKeyword.builder().id(1964).name("cave").build()));

    }

}
