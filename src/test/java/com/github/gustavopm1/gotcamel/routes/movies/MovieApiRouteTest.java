package com.github.gustavopm1.gotcamel.routes.movies;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.gustavopm1.gotcamel.models.Request;
import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.SearchType;
import com.github.gustavopm1.gotcamel.models.movie.*;
import com.github.gustavopm1.gotcamel.routes.AbstractRouteTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class MovieApiRouteTest extends AbstractRouteTest {



    @Test
    public void testRouteMovie() throws IOException {

        Response<Movie> returned = sendMessage(
                configuration.getRoutes().getInbound().getMovie(),
                Request.<TypeValueData>builder().body( TypeValueData.builder().type(SearchType.MOVIENAME).value("13th Warrior").build() ).user("testuser").build(),
                NO_HEADERS,
                (new TypeReference<Response<Movie>>(){})
        );

        assertThat(returned)
                .isNotNull()
                .hasFieldOrPropertyWithValue("found",true)
                .hasFieldOrProperty("body");

        assertThat(returned.getBody())
                .isNotNull()
                .isInstanceOf(Movie.class)
                .hasFieldOrPropertyWithValue("original_title","13th Warrior")
                .hasFieldOrPropertyWithValue("year",1999);

    }

    @Test
    public void testRouteMovieById() throws IOException {

        Response<Movie> returned = sendMessage(
                configuration.getRoutes().getInbound().getMovie(),
                Request.<TypeValueData>builder().body( TypeValueData.builder().type(SearchType.MOVIEID).value("1911").build() ).user("testuser").build(),
                NO_HEADERS,
                (new TypeReference<Response<Movie>>(){})
        );

        assertThat(returned)
                .isNotNull()
                .hasFieldOrPropertyWithValue("found",true)
                .hasFieldOrProperty("body");

        assertThat(returned.getBody())
                .isNotNull()
                .isInstanceOf(Movie.class)
                .hasFieldOrPropertyWithValue("original_title","The 13th Warrior")
                .hasFieldOrPropertyWithValue("year",0);

    }

    @Test
    public void testCompleteRouteMovieById() throws IOException{

        Response<Movie> returned = sendMessage(
                configuration.getRoutes().getInbound().getMovie(),
                Request.<TypeValueData>builder().body( TypeValueData.builder().type(SearchType.FULLMOVIE).value("1911").build() ).user("testuser").build(),
                NO_HEADERS,
                (new TypeReference<Response<Movie>>(){})
        );

        assertThat(returned)
                .isNotNull()
                .hasFieldOrPropertyWithValue("found",true)
                .hasFieldOrProperty("body");

        assertThat(returned.getBody())
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