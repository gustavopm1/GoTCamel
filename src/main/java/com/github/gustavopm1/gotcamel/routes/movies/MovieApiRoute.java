package com.github.gustavopm1.gotcamel.routes.movies;

import com.github.gustavopm1.gotcamel.GotCamelConstants;
import com.github.gustavopm1.gotcamel.configuration.GotCamelConfiguration;
import com.github.gustavopm1.gotcamel.marshallers.movie.MovieMarshaller;
import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.routes.MainRouteBuilder;
import com.github.gustavopm1.gotcamel.services.movie.MovieCastService;
import com.github.gustavopm1.gotcamel.services.movie.MovieCrewService;
import com.github.gustavopm1.gotcamel.services.movie.MovieKeywordsService;
import com.github.gustavopm1.gotcamel.services.movie.MovieSearchService;
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
public class MovieApiRoute extends MainRouteBuilder {

    @Autowired
    @Setter
    private GotCamelConfiguration configuration;

    @Autowired
    @Setter
    private MovieSearchService service;

    @Autowired
    @Setter
    private MovieCrewService movieCrewService;

    @Autowired
    @Setter
    private MovieKeywordsService movieKeywordsService;

    @Autowired
    @Setter
    private MovieCastService movieCastService;

    @Autowired
    @Setter
    private MovieMarshaller marshaller;

    @Override
    public String getFrom() { return configuration.getRoutes().getInbound().getMovie(); }

    @Override
    public String getRouteId() {return configuration.getIds().getMovie(); }

    @Override
    public String getRouteName() {return configuration.getNames().getMovie(); }

    @Override
    public void routeConfigure(RouteDefinition processor) {

        processor
                .log(LoggingLevel.INFO,log,getRouteId(), GotCamelConstants.ROUTE_START_KEY + " :: " + getRouteName())
                .unmarshal(marshaller)
                .log(LoggingLevel.INFO,log,getRouteId(), "Headers are ${headers}")
                .choice()
                    .when(isFindMovieByName())
                        .bean(service,"getMovie").id("getMovieServiceBean")
                    .endChoice()

                    .when(isFindMovieCastByMovieId())
                        .bean(service,"getMovie").id("getMovieServiceOnCastIdBean")
                        .bean(movieCastService,"getMovieCastById").id("getMovieCastByIdServiceBean")
                    .endChoice()

                    .when(isFindMovieCastByMovieName())
                        .bean(service,"getMovie").id("getMovieServiceOnCastNameBean")
                        .bean(movieCastService,"getMovieCastByName").id("getMovieCastByNameServiceBean")
                    .endChoice()

                    .when(isFindMovieCrewByMovieId())
                        .bean(service,"getMovie").id("getMovieServiceOnCrewIdBean")
                        .bean(movieCrewService,"getMovieCrewById").id("getMovieCrewByIdServiceBean")
                    .endChoice()

                    .when(isFindMovieCrewByMovieName())
                        .bean(service,"getMovie").id("getMovieServiceOnCrewNameBean")
                        .bean(movieCrewService,"getMovieCrewByName").id("getMovieCrewByNameServiceBean")
                    .endChoice()

                    .when(isFindMovieKeywordsByMovieName())
                        .bean(service,"getMovie").id("getMovieServiceOnKeywordsNameBean")
                        .bean(movieKeywordsService,"getMovieKeywordsByName").id("getMovieKeywordsByNameServiceBean")
                    .endChoice()

                    .when(isFindMovieKeywordsByMovieId())
                        .bean(service,"getMovie").id("getMovieServiceOnKeywordsIdBean")
                        .bean(movieKeywordsService,"getMovieKeywordsById").id("getMovieKeywordsByIdServiceBean")
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
