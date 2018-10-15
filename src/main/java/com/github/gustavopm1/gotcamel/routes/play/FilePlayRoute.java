package com.github.gustavopm1.gotcamel.routes.play;

import com.github.gustavopm1.gotcamel.configuration.GotCamelConfiguration;
import com.github.gustavopm1.gotcamel.marshallers.play.PlayMarshaller;
import com.github.gustavopm1.gotcamel.routes.MainRouteBuilder;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.LoggingLevel;
import org.apache.camel.model.RouteDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FilePlayRoute extends MainRouteBuilder {

    @Autowired
    @Setter
    private GotCamelConfiguration configuration;

    @Autowired
    @Setter
    private PlayMarshaller marshaller;

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
                        .when(body().startsWith("Hello"))
                            .log(LoggingLevel.INFO,log,getRouteId(),"Started with hello")
                        .endChoice()
                        .otherwise()
                    .log("${body}")
                .end()
                .log("End of "+getRouteId());

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
