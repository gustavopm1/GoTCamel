package com.github.gustavopm1.gotcamel.routes.play;

import com.github.gustavopm1.gotcamel.configuration.GotCamelConfiguration;
import com.github.gustavopm1.gotcamel.marshallers.play.PlayMarshaller;
import com.github.gustavopm1.gotcamel.routes.MainRouteBuilder;
import com.github.gustavopm1.gotcamel.services.play.FilePlayService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.LoggingLevel;
import org.apache.camel.model.RouteDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Component
@Slf4j
public class FilePlayRoute extends MainRouteBuilder {

    @Autowired
    @Setter
    private GotCamelConfiguration configuration;

    @Autowired
    @Setter
    private PlayMarshaller marshaller;

    @Autowired
    private FilePlayService filePlayService;

    @Override
    public String getFrom() { return configuration.getRoutes().getPlayfile(); }

    @Override
    public String getRouteId() { return configuration.getIds().getPlayfile(); }

    @Override
    public void routeConfigure(RouteDefinition processor) {



        processor
                .log(LoggingLevel.INFO,log,getRouteId(),ROUTE_START_KEY + " :: Play File")
                .unmarshal(marshaller)
                .log(LoggingLevel.INFO,log,getRouteId(),"Processing ${body.size()} lines")
                .split(simple("${body}"))
                    .choice()
                        .when(body().startsWith("Hello")).when(body().isInstanceOf(String.class))
                            .log(LoggingLevel.INFO,log,getRouteId(),"Started with hello")
                        .endChoice()
                        .otherwise()
                    .log("${body}")
                .end()
                .bean(filePlayService, "toUpperCase").id("filePlayServiceToUpperCase")
                .log("End of "+getRouteId())
                .to("file:C:/Java Projects/GoTCamel/output");

    }

    @Override
    public void routeExceptions(RouteDefinition processor) {
        processor
                .onException(Exception.class)
                .handled(true)
                .log("Ocorreu um erro ao tratar o dado da fila "+getRouteId()+" Um dado de tipo desconhecido foi recebido")
                .end();

    }

}
