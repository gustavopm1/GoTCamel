package com.github.gustavopm1.gotcamel.routes.experimental;

import com.github.gustavopm1.gotcamel.GotCamelConstants;
import com.github.gustavopm1.gotcamel.marshallers.experimental.ExperimentalMarshaller;
import com.github.gustavopm1.gotcamel.routes.MainRouteBuilder;
import com.github.gustavopm1.gotcamel.services.experimental.ExperimentalService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.github.gustavopm1.gotcamel.predicates.Predicates.isFindArtistById;
import static com.github.gustavopm1.gotcamel.predicates.Predicates.isFindArtistByName;

@Component
@Slf4j
public class ExperimentalEntryRoute extends MainRouteBuilder {

    @Autowired
    @Setter
    private ExperimentalMarshaller marshaller;

    @Autowired
    @Setter
    private ExperimentalService service;

    @Override
    public String getFrom() { return configuration.getRoutes().getInbound().getExperimental(); }

    @Override
    public String getRouteId() { return configuration.getIds().getExperimental(); }

    @Override
    public String getRouteName() { return configuration.getNames().getExperimental(); }

    @Override
    public void routeConfigure(RouteDefinition processor) {

        processor
            .log(LoggingLevel.INFO, log, getRouteId(), GotCamelConstants.ROUTE_START_KEY + " :: " + getRouteName())
            .unmarshal(marshaller)
            .log(LoggingLevel.INFO,log,getRouteId(), "Experimenting Headers are ${headers}")
            .bean(service,"experimentSend").id("experimentSendBean")
            .marshal().json(JsonLibrary.Jackson,true)
            .convertBodyTo(String.class)
            .log(LoggingLevel.INFO,log,getRouteId(),GotCamelConstants.ROUTE_END_KEY  + " :: " + getRouteName());

    }

    @Override
    public void routeExceptions(RouteDefinition processor) {

    }
}
