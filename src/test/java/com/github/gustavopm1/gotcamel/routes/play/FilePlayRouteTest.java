package com.github.gustavopm1.gotcamel.routes.play;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.gustavopm1.gotcamel.routes.AbstractRouteTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class FilePlayRouteTest extends AbstractRouteTest {


    @Test
    public void testRouteHello() throws JsonProcessingException {

        Object returned = sendMessage(
                configuration.getRoutes().getInbound().getPlayfile(),
                "Paulo",
                NO_HEADERS
                );

        assertThat(returned)
                .isNotNull()
                .isInstanceOf(ArrayList.class)
                .asList()
                .isNotEmpty()
                .contains("PAULO");

    }




}