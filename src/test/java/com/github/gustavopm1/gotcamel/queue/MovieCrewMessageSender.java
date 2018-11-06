package com.github.gustavopm1.gotcamel.queue;

import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.SearchType;
import com.github.gustavopm1.gotcamel.models.movie.Crew;
import com.github.gustavopm1.gotcamel.models.movie.Movie;
import com.github.gustavopm1.gotcamel.models.movie.TypeValueData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.JMSException;
import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"test","prod"})
public class MovieCrewMessageSender extends AbstractQueueTest {

    @Test
    public void shouldSendMessageToQueueAndGetBackResultMovieCrewByID() throws IOException, JMSException, ClassNotFoundException {
        String movieID = "1911";
        Response<Movie> response = exchange("movie.requests", TypeValueData.builder().type(SearchType.CREWMOVIEID).value(movieID).build());

        assertThat(response)
                .isNotNull()
                .isInstanceOf(Response.class)
                .hasFieldOrPropertyWithValue("found",true);

        assertThat(response.getBody())
                .isNotNull()
                .isInstanceOf(Movie.class)
                .hasFieldOrPropertyWithValue("original_title","The 13th Warrior")
                .hasFieldOrPropertyWithValue("crew", Arrays.asList(Crew.builder().id(19893).name("Warren Lewis").department("Writing").build(),
                        Crew.builder().id(1090).name("John McTiernan").department("Directing").build()));
    }
}
