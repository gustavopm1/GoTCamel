package com.github.gustavopm1.gotcamel.routes.experimental;


import com.fasterxml.jackson.core.type.TypeReference;
import com.github.gustavopm1.gotcamel.models.Request;
import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.SearchType;
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
import java.lang.reflect.Type;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
@ActiveProfiles({"route","prod"})
public class ExperimentalEntryRouteTest extends AbstractRouteTest {


    @Test
    public void testExperiment() throws IOException {

        String returned = sendMessage(
                configuration.getRoutes().getInbound().getExperimental(),
                Request.<TypeValueData>builder().body( TypeValueData.builder().type(SearchType.MOVIENAME).value("The 13th Warrior").build() ).user("testuser").build(),
                NO_HEADERS,
                String.class
        );

        log.info("\n\n Eu experimentei um novo sabor \n{}\n\n",returned);

        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
