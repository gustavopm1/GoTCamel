package com.github.gustavopm1.gotcamel.routes.lastfm.music;

import com.github.gustavopm1.gotcamel.GotCamelConstants;
import com.github.gustavopm1.gotcamel.marshallers.lastfm.ArtistMarshaller;
import com.github.gustavopm1.gotcamel.routes.MainRouteBuilder;
import com.github.gustavopm1.gotcamel.services.lastfm.music.ArtistSearchByIdService;
import com.github.gustavopm1.gotcamel.services.lastfm.music.ArtistSearchService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.github.gustavopm1.gotcamel.predicates.Predicates.*;

@Component
@Slf4j
public class ArtistApiRoute extends MainRouteBuilder {

    @Autowired
    @Setter
    private ArtistSearchByIdService artistSearchByIdService;

    @Autowired
    @Setter
    private ArtistSearchService artistSearchService;

    @Autowired
    @Setter
    private ArtistMarshaller marshaller;

    @Override
    public String getFrom() {
        return configuration.getRoutes().getInbound().getArtist();
    }

    @Override
    public String getRouteId() {
        return configuration.getIds().getArtist();
    }

    @Override
    public String getRouteName() {
        return configuration.getNames().getArtist();
    }

    @Override
    public void routeConfigure(RouteDefinition processor) {

        processor
                .log(LoggingLevel.INFO, log, getRouteId(), GotCamelConstants.ROUTE_START_KEY + " :: " + getRouteName())
                .unmarshal(marshaller)
                .log(LoggingLevel.INFO,log,getRouteId(), "Headers are ${headers}")
                .choice()
                    .when(isFindArtistById())
                        .bean(artistSearchByIdService,"getArtistById").id("getArtistByIdBean")
                    .endChoice()

                    .when(isFindArtistByName())
                    .bean(artistSearchService,"getArtist").id("getArtistBean")
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
