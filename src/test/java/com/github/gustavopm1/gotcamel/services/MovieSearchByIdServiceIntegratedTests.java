package com.github.gustavopm1.gotcamel.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.gustavopm1.gotcamel.exceptions.movie.MovieNotFoundException;
import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.SearchType;
import com.github.gustavopm1.gotcamel.models.movie.Crew;
import com.github.gustavopm1.gotcamel.models.movie.Movie;
import com.github.gustavopm1.gotcamel.models.movie.MovieCast;
import com.github.gustavopm1.gotcamel.models.movie.MovieKeyword;
import com.github.gustavopm1.gotcamel.services.movie.MovieCastService;
import com.github.gustavopm1.gotcamel.services.movie.MovieCrewService;
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
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"route","prod"})
public class MovieSearchByIdServiceIntegratedTests {

    @Autowired
    MovieSearchByIdService movieSearchByIdService;

    @Autowired
    MovieKeywordsService movieKeywordsService;

    @Autowired
    MovieCastService movieCastService;

    @Autowired
    MovieCrewService movieCrewService;

    @Test
    public void integratedTestGETMovieById() throws MovieNotFoundException {

        Movie movie = Movie.builder()
                .id(1911)
                .runtime(102)
                .original_title("The 13th Warrior")
                .overview("In AD 922, Arab courtier, Ahmad Ibn Fadlan accompanies a party of Vikings to the barbaric North to combat a terror that slaughters Vikings and devours their flesh.")
                .build();

        HashMap< String, Object> headers = new HashMap<>();

        headers.put(TYPE_NAME, SearchType.MOVIEID);
        headers.put(TYPE_VALUE, Integer.toString(movie.getId()));

        Response<Movie> response = movieSearchByIdService.getMovieById("1911",headers);

        assertEquals(movie.getId(),response.getBody().getId());
        assertEquals(movie.getOriginal_title(), response.getBody().getOriginal_title());

        }

        @Test
        public void integratedTestGETFullMovieById() throws MovieNotFoundException {

            Movie movie = Movie.builder()
                    .id(1911)
                    .runtime(102)
                    .original_title("The 13th Warrior")
                    .overview("In AD 922, Arab courtier, Ahmad Ibn Fadlan accompanies a party of Vikings to the barbaric North to combat a terror that slaughters Vikings and devours their flesh.")
                    .build();

            HashMap< String, Object> headers = new HashMap<>();

            headers.put(TYPE_NAME, SearchType.FULLMOVIE);
            headers.put(TYPE_VALUE, Integer.toString(movie.getId()));

            Response<Movie> response = movieSearchByIdService.getMovieById("1911", headers);

            response = movieKeywordsService.getMovieKeywordsById(Integer.toString(response.getBody().getId()),response,headers);

            response = movieCastService.getMovieCastById(response.getBody().getId(),response,headers);

            response = movieCrewService.getMovieCrewById(Integer.toString(response.getBody().getId()),response,headers);

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
                            MovieKeyword.builder().id(1964).name("cave").build()))
                    .hasFieldOrPropertyWithValue("crew", Arrays.asList(Crew.builder().id(19893).name("Warren Lewis").department("Writing").build(),
                            Crew.builder().id(1090).name("John McTiernan").department("Directing").build()))
                    .hasFieldOrPropertyWithValue("cast", Arrays.asList(MovieCast.builder()
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
                                    .build()));
        }
}

