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

import static com.github.gustavopm1.gotcamel.predicates.play.FilePlayPredicates.*;


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
    public String getFrom() { return configuration.getRoutes().getInbound().getPlayfile(); }

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
                        .when(isFilePlayHello())
                            .log(LoggingLevel.INFO,log,getRouteId(),"Started with hello")
                        .when(isFilePlayGreetName())
                            .bean(filePlayService, "greetName").id("filePlayServiceGreetName")
                            .log(LoggingLevel.INFO,log,getRouteId(),"Hello Gustavo!")
                        .when(isFilePlayCatMeow())
                            .bean(filePlayService, "meowCat").id("filePlayServiceMeowCat")
                            .log(LoggingLevel.INFO,log,"The cat is meowing!!")
                        .when(isStatsTxtFile())
                            .bean(filePlayService, "writeInStats").id("filePlayServiceWriteInStats")
                            .log(LoggingLevel.INFO,log,"Writing in Stats.txt File")
                    .endChoice()
                        .otherwise()
                    .log("${body} - ${headers}")
                .end()
                .bean(filePlayService, "toUpperCase").id("filePlayServiceToUpperCase")
                .log("End of "+getRouteId())
                .to(configuration.getRoutes().getOutbound().getPlayfile());

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
