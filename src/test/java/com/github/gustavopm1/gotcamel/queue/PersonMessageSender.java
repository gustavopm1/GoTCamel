package com.github.gustavopm1.gotcamel.queue;

import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.SearchType;
import com.github.gustavopm1.gotcamel.models.movie.Movie;
import com.github.gustavopm1.gotcamel.models.movie.TypeValueData;
import com.github.gustavopm1.gotcamel.models.person.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"test","prod"})
public class PersonMessageSender extends AbstractQueueTest {


    @Test
    public void shouldSendMessageToQueueAndGetBackResultPersonNotFound() throws IOException{
        String movieID = "6777";
        Response<Movie> response = exchangeMovie("movie.requests", TypeValueData.builder().type(SearchType.MOVIEID).value(movieID).build());

        assertThat(response)
                .isNotNull()
                .isInstanceOf(Response.class)
                .hasFieldOrPropertyWithValue("found",false);
    }


    @Test
    public void shouldSendMessageToQueueAndGetBackResultPersonByID() throws IOException {
        String personID = "6677";
        Response<Person> response = exchangePerson("person.requests", TypeValueData.builder().type(SearchType.PERSONID).value(personID).build());

        assertThat(response)
                .isNotNull()
                .isInstanceOf(Response.class)
                .hasFieldOrPropertyWithValue("found",true);

        assertThat(response.getBody())
                .isNotNull()
                .isInstanceOf(Person.class)
                .hasFieldOrPropertyWithValue("name","Kyle MacLachlan")
                .hasFieldOrPropertyWithValue("id",6677)
                .hasFieldOrPropertyWithValue("biography","Kyle Merritt MacLachlan (born February 22, 1959) is an American actor. MacLachlan widely known for his portrayal of Dale Cooper in the " +
                        "TV series Twin Peaks (1990–1991; 2017), and its prequel film Twin Peaks: Fire Walk with Me (1992). He is also known for his film roles including " +
                        "cult films such as Dune (1984), Blue Velvet (1986), The Hidden (1987), and Showgirls (1995). He has also had prominent roles in other television" +
                        " shows including appearing as Trey MacDougal in Sex and the City (2000–2002), Orson Hodge in Desperate Housewives (2006–2012), " +
                        "The Captain in How I Met Your Mother (2010–2014), the Mayor of Portland in Portlandia (2011–2018), and as Calvin Zabo in Agents of S.H.I.E.L.D. (2014–2015)." +
                        "\n\nMacLachlan was born in Yakima, Washington. His mother, Catherine (née Stone), was a public relations director, and his father, Kent Alan MacLachlan, was a stockbroker" +
                        " and lawyer. He has Scottish, Cornish and German ancestry. He has two younger brothers named Craig and Kent, both of whom live in the Pacific Northwest. MacLachlan graduated" +
                        " from Eisenhower High School in 1977. He graduated from the University of Washington in 1982 and, shortly afterward, moved to Hollywood, California to pursue his career.")
                .hasFieldOrPropertyWithValue("place_of_birth","Yakima - Washington - USA")
                .hasFieldOrPropertyWithValue("profile_path","/7DnMuDlSdpycAQQxOIDmV66qerc.jpg");

    }

    @Test
    public void shouldSendMessageToQueueAndGetBackResultByName() throws IOException {
        String personName = "Kyle MacLachlan";
        Response<Person> response = exchangePerson("person.requests", TypeValueData.builder().type(SearchType.PERSONNAME).value(personName).build());

        assertThat(response)
                .isNotNull()
                .isInstanceOf(Response.class)
                .hasFieldOrPropertyWithValue("found",true);

        assertThat(response.getBody())
                .isNotNull()
                .isInstanceOf(Person.class)
                .hasFieldOrPropertyWithValue("name","Kyle MacLachlan")
                .hasFieldOrPropertyWithValue("id",6677);
    }
}
