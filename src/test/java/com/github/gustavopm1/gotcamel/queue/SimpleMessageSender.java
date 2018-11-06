package com.github.gustavopm1.gotcamel.queue;

import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.SearchType;
import com.github.gustavopm1.gotcamel.models.movie.Movie;
import com.github.gustavopm1.gotcamel.models.movie.TypeValueData;
import com.github.gustavopm1.gotcamel.test.annotations.MethodName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.JMSException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"test","prod"})
public class SimpleMessageSender extends AbstractQueueTest {



    @Test
    @MethodName("bruceLee")
    public void shouldSendMessageToQueueAndGetBackResult() throws IOException, JMSException, ClassNotFoundException {
        String movieID = "1911";
        Response<Movie> response = exchange("movie.requests", TypeValueData.builder().type(SearchType.MOVIEID).value(movieID).build());

        assertThat(response)
                .isNotNull()
                .isInstanceOf(Response.class)
                .hasFieldOrPropertyWithValue("found",false);
    }
}
