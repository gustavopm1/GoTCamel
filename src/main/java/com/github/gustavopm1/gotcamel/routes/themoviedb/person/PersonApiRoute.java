package com.github.gustavopm1.gotcamel.routes.themoviedb.person;

import com.github.gustavopm1.gotcamel.GotCamelConstants;
import com.github.gustavopm1.gotcamel.configuration.GotCamelConfiguration;
import com.github.gustavopm1.gotcamel.marshallers.themoviedb.person.PersonMarshaller;
import com.github.gustavopm1.gotcamel.routes.MainRouteBuilder;
import com.github.gustavopm1.gotcamel.services.themoviedb.person.PersonSearchByIdService;
import com.github.gustavopm1.gotcamel.services.themoviedb.person.PersonSearchService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.github.gustavopm1.gotcamel.predicates.Predicates.isFindPersonById;
import static com.github.gustavopm1.gotcamel.predicates.Predicates.isFindPersonByName;

@Component
@Slf4j
public class PersonApiRoute extends MainRouteBuilder {

    @Autowired
    @Setter
    private PersonSearchByIdService personSearchByIdService;

    @Autowired
    @Setter
    private PersonSearchService personSearchService;

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
                .choice()
                    .when(isFindPersonById())
                        .bean(personSearchByIdService,"getPersonById").id("getPersonByIdServiceBean")
                    .endChoice()
                    .when(isFindPersonByName())
                        .bean(personSearchService,"getPerson").id("getPersonByNameServiceBean")
                    .endChoice()
                .otherwise()
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {

                    }
                })
                .end()
                .marshal().json(JsonLibrary.Jackson,true)
                .convertBodyTo(String.class)
                .log(LoggingLevel.INFO,log,getRouteId(),GotCamelConstants.ROUTE_END_KEY  + " :: " + getRouteName());

    }

    @Override
    public void routeExceptions(RouteDefinition processor) {

    }
}
