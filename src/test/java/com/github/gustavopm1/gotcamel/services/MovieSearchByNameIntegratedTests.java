package com.github.gustavopm1.gotcamel.services;

import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.SearchType;
import com.github.gustavopm1.gotcamel.models.movie.Movie;
import com.github.gustavopm1.gotcamel.services.movie.MovieSearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static com.github.gustavopm1.gotcamel.GotCamelConstants.TYPE_NAME;
import static com.github.gustavopm1.gotcamel.GotCamelConstants.TYPE_VALUE;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"test","prod"})
public class MovieSearchByNameIntegratedTests {

    @Autowired
    MovieSearchService movieSearchService;

    @Test
    public void integratedTestGETMovieByName(){
        Movie movie = Movie.builder()
                .id(1911)
                .runtime(102)
                .original_title("The 13th Warrior")
                .overview("In AD 922, Arab courtier, Ahmad Ibn Fadlan accompanies a party of Vikings to the barbaric North to combat a terror that slaughters Vikings and devours their flesh.")
                .build();

        HashMap< String, Object> headers = new HashMap<>();

        headers.put(TYPE_NAME, SearchType.MOVIENAME);
        headers.put(TYPE_VALUE, movie.getOriginal_title());

        Response<Movie> response = movieSearchService.getMovie("The 13th Warrior", headers);

        assertEquals(movie.getId(),response.getBody().getId());

    }

}
