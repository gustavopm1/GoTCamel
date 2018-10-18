package com.github.gustavopm1.gotcamel.routes.movies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.gustavopm1.gotcamel.models.Request;
import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.movie.Movie;
import com.github.gustavopm1.gotcamel.models.movie.TypeValueData;
import com.github.gustavopm1.gotcamel.routes.AbstractRouteTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class MovieApiRouteTest extends AbstractRouteTest {

    @Test
    public void testRouteHello() throws IOException {

        Response<Movie> returned = sendMessage(
                configuration.getRoutes().getInbound().getMovie(),
                Request.<TypeValueData>builder().body( TypeValueData.builder().type("movie").value("13th Warrior").build() ).user("testuser").build(),
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
                .hasFieldOrPropertyWithValue("name","13th Warrior")
                .hasFieldOrPropertyWithValue("year",1999);

    }

}