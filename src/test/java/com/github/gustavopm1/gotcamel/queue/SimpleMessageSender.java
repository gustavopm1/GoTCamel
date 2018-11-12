package com.github.gustavopm1.gotcamel.queue;

import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.SearchType;
import com.github.gustavopm1.gotcamel.models.themoviedb.movie.*;
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
public class SimpleMessageSender extends AbstractQueueTest {



    @Test
    public void shouldSendMessageToQueueAndGetBackResultNotFound() throws IOException{
        String movieID = "1191";
        Response<Movie> response = exchangeMovie("movie.requests", TypeValueData.builder().type(SearchType.MOVIEID).value(movieID).build());

        assertThat(response)
                .isNotNull()
                .isInstanceOf(Response.class)
                .hasFieldOrPropertyWithValue("found",false);
    }

    @Test
    public void shouldSendMessageToQueueAndGetBackResultByID() throws IOException{
        String movieID = "1911";
        Response<Movie> response = exchangeMovie("movie.requests", TypeValueData.builder().type(SearchType.MOVIEID).value(movieID).build());

        assertThat(response)
                .isNotNull()
                .isInstanceOf(Response.class)
                .hasFieldOrPropertyWithValue("found",true);

        assertThat(response.getBody())
                .isNotNull()
                .isInstanceOf(Movie.class)
                .hasFieldOrPropertyWithValue("original_title","The 13th Warrior");
    }

    @Test
    public void shouldSendMessageToQueueAndGetBackResultByName() throws IOException {
        String movieName = "The 13th Warrior";
        Response<Movie> response = exchangeMovie("movie.requests", TypeValueData.builder().type(SearchType.MOVIENAME).value(movieName).build());

        assertThat(response)
                .isNotNull()
                .isInstanceOf(Response.class)
                .hasFieldOrPropertyWithValue("found",true);

        assertThat(response.getBody())
                .isNotNull()
                .isInstanceOf(Movie.class)
                .hasFieldOrPropertyWithValue("original_title","The 13th Warrior");
    }

    @Test
    public void shouldSendMessageToQueueAndGetBackResulFullMovietByID() throws IOException {
        String movieID = "1911";
        Response<Movie> response = exchangeMovie("movie.requests", TypeValueData.builder().type(SearchType.FULLMOVIE).value(movieID).build());

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
