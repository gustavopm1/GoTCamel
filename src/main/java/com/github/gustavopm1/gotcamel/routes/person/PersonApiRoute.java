package com.github.gustavopm1.gotcamel.routes.person;

import com.github.gustavopm1.gotcamel.GotCamelConstants;
import com.github.gustavopm1.gotcamel.configuration.GotCamelConfiguration;
import com.github.gustavopm1.gotcamel.marshallers.person.PersonMarshaller;
import com.github.gustavopm1.gotcamel.routes.MainRouteBuilder;
import com.github.gustavopm1.gotcamel.services.person.PersonSearchService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.LoggingLevel;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PersonApiRoute extends MainRouteBuilder {

    @Autowired
    @Setter
    private GotCamelConfiguration configuration;

    @Autowired
    @Setter
    private PersonSearchService service;

    @Autowired
    @Setter
    private PersonMarshaller marshaller;


    @Override
    public String getFrom() {
        return configuration.getRoutes().getInbound().getPerson();
    }

    @Override
    public String getRouteId() {
        return configuration.getIds().getPerson();
    }

    @Override
    public String getRouteName() {
        return configuration.getNames().getPerson();
    }

    @Override
    public void routeConfigure(RouteDefinition processor) {
        processor
                .log(LoggingLevel.INFO,log,getRouteId(), GotCamelConstants.ROUTE_START_KEY + " :: " + getRouteName())
                .unmarshal(marshaller)
                .log(LoggingLevel.INFO,log,getRouteId(), "Headers are ${headers}")
                .bean(service,"getPerson").id("getPersonServiceBean")

                .marshal().json(JsonLibrary.Jackson,true)
                .convertBodyTo(String.class)
                .log(LoggingLevel.INFO,log,getRouteId(),GotCamelConstants.ROUTE_END_KEY  + " :: " + getRouteName());

    }

    @Override
    public void routeExceptions(RouteDefinition processor) {

    }
}
