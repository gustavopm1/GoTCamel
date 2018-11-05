package com.github.gustavopm1.gotcamel.routes.movies;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.gustavopm1.gotcamel.models.Request;
import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.SearchType;
import com.github.gustavopm1.gotcamel.models.movie.Movie;
import com.github.gustavopm1.gotcamel.models.movie.MovieCast;
import com.github.gustavopm1.gotcamel.models.movie.TypeValueData;
import com.github.gustavopm1.gotcamel.routes.AbstractRouteTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
@ActiveProfiles({"test","prod"})
public class MovieCastApiRouteTest extends AbstractRouteTest {

    @Test
    public void testRouteSetCastById() throws IOException {

        Response<Movie> returned = sendMessage(
                configuration.getRoutes().getInbound().getMovie(),
                Request.<TypeValueData>builder().body( TypeValueData.builder().type(SearchType.CASTMOVIEID).value("1911").build() ).user("testuser").build(),
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
