package com.github.gustavopm1.gotcamel.routes.themoviedb.movies;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.gustavopm1.gotcamel.models.Request;
import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.SearchType;
import com.github.gustavopm1.gotcamel.models.themoviedb.movie.Crew;
import com.github.gustavopm1.gotcamel.models.themoviedb.movie.Movie;
import com.github.gustavopm1.gotcamel.models.themoviedb.movie.TypeValueData;
import com.github.gustavopm1.gotcamel.routes.AbstractRouteTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
@ActiveProfiles({"route","prod"})
public class MovieCrewApiRouteTest extends AbstractRouteTest {

    @Test
    public void testRouteSetCrewByMovieId() throws IOException{

        Response<Movie> returned = sendMessage(
                configuration.getRoutes().getInbound().getMovie(),
                Request.<TypeValueData>builder().body(TypeValueData.builder().type(SearchType.CREWMOVIEID)
                        .value("1911").build()).user("testuser").build(),
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
                .hasFieldOrPropertyWithValue("crew", Arrays.asList(Crew.builder().id(19893).name("Warren Lewis").department("Writing").build(),
                                                                         Crew.builder().id(1090).name("John McTiernan").department("Directing").build()));

    }

}
