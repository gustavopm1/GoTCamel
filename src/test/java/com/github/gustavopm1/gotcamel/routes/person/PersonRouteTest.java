package com.github.gustavopm1.gotcamel.routes.person;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.gustavopm1.gotcamel.models.Request;
import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.SearchType;
import com.github.gustavopm1.gotcamel.models.movie.TypeValueData;
import com.github.gustavopm1.gotcamel.models.person.Person;
import com.github.gustavopm1.gotcamel.routes.AbstractRouteTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
@ActiveProfiles({"route","prod"})
public class PersonRouteTest extends AbstractRouteTest {

    @Test
    public void testSearchPerson() throws IOException {
        Response<Person> returned = sendMessage(
                configuration.getRoutes().getInbound().getPerson(),
                Request.<TypeValueData>builder().body( TypeValueData.builder().type(SearchType.PERSONID).value("6677").build() ).user("testuser").build(),
                NO_HEADERS,
                (new TypeReference<Response<Person>>(){})
        );

        assertThat(returned)
                .isNotNull()
                .hasFieldOrPropertyWithValue("found",true)
                .hasFieldOrProperty("body");

        assertThat(returned.getBody())
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
    public void testSearchPersonByName() throws IOException{
        Response<Person> returned = sendMessage(
                configuration.getRoutes().getInbound().getPerson(),
                Request.<TypeValueData>builder().body( TypeValueData.builder().type(SearchType.PERSONNAME).value("Kyle MacLachlan").build() ).user("testuser").build(),
                NO_HEADERS,
                (new TypeReference<Response<Person>>(){})
        );

        assertThat(returned)
                .isNotNull()
                .hasFieldOrPropertyWithValue("found",true)
                .hasFieldOrProperty("body");

        assertThat(returned.getBody())
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


}
