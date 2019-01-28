package com.github.gustavopm1.gotcamel.routes.experimental;

import com.github.gustavopm1.gotcamel.GotCamelConstants;
import com.github.gustavopm1.gotcamel.marshallers.experimental.ExperimentalMarshaller;
import com.github.gustavopm1.gotcamel.routes.MainRouteBuilder;
import com.github.gustavopm1.gotcamel.services.experimental.ExperimentalService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.LoggingLevel;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExperimentalPatternRoute extends MainRouteBuilder {

    @Autowired
    @Setter
    private ExperimentalMarshaller marshaller;

    @Autowired
    @Setter
    private ExperimentalService service;

    @Override
    public String getFrom() { return configuration.getRoutes().getInbound().getExperimentalPattern(); }

    @Override
    public String getRouteId() { return configuration.getIds().getExperimentalPattern(); }

    @Override
    public String getRouteName() { return configuration.getNames().getExperimentalPattern(); }

    @Override
    public void routeConfigure(RouteDefinition processor) {

        processor
            .log(LoggingLevel.INFO, log, getRouteId(), GotCamelConstants.ROUTE_START_KEY + " :: " + getRouteName())
            .log(LoggingLevel.INFO,log,getRouteId(), "Experimenting Pattern Headers are ${headers}")
            .bean(service,"experimentReceive").id("experimentReceiveBean")
            .marshal().json(JsonLibrary.Jackson,true)
            .convertBodyTo(String.class)
            .log(LoggingLevel.INFO,log,getRouteId(),GotCamelConstants.ROUTE_END_KEY  + " :: " + getRouteName());

    }

    @Override
    public void routeExceptions(RouteDefinition processor) {

    }
}
