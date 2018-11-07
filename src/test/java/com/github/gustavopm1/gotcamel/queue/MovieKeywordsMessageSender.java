package com.github.gustavopm1.gotcamel.queue;

import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.SearchType;
import com.github.gustavopm1.gotcamel.models.movie.Movie;
import com.github.gustavopm1.gotcamel.models.movie.MovieKeyword;
import com.github.gustavopm1.gotcamel.models.movie.TypeValueData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"test","prod"})
public class MovieKeywordsMessageSender extends AbstractQueueTest {

    @Test
    public void shouldSendMessageToQueueAndGetBackResultMovieKeywordsByID() throws IOException {
        String movieID = "1911";
        Response<Movie> response = exchangeMovie("movie.requests", TypeValueData.builder().type(SearchType.KEYWORDSMOVIEID).value(movieID).build());

        assertThat(response)
                .isNotNull()
                .isInstanceOf(Response.class)
                .hasFieldOrPropertyWithValue("found",true);

        assertThat(response.getBody())
                .isNotNull()
                .isInstanceOf(Movie.class)
                .hasFieldOrPropertyWithValue("original_title","The 13th Warrior")
                .hasFieldOrPropertyWithValue("keywords", Arrays.asList(MovieKeyword.builder().id(616).name("witch").build(),
                        MovieKeyword.builder().id(1585).name("snake").build(),
                        MovieKeyword.builder().id(1964).name("cave").build()));
    }

}
