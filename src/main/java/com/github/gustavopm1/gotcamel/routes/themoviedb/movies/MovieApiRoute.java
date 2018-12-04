package com.github.gustavopm1.gotcamel.routes.themoviedb.movies;

import com.github.gustavopm1.gotcamel.GotCamelConstants;
import com.github.gustavopm1.gotcamel.exceptions.TooManyRequestsException;
import com.github.gustavopm1.gotcamel.exceptions.themoviedb.movie.MovieNotFoundException;
import com.github.gustavopm1.gotcamel.marshallers.themoviedb.movie.MovieMarshaller;
import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.themoviedb.movie.Movie;
import com.github.gustavopm1.gotcamel.routes.MainRouteBuilder;
import com.github.gustavopm1.gotcamel.services.themoviedb.movie.MovieCastService;
import com.github.gustavopm1.gotcamel.services.themoviedb.movie.MovieCrewService;
import com.github.gustavopm1.gotcamel.services.themoviedb.movie.MovieKeywordsService;
import com.github.gustavopm1.gotcamel.services.themoviedb.movie.MovieSearchByIdService;
import com.github.gustavopm1.gotcamel.services.themoviedb.movie.MovieSearchService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.Builder;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.github.gustavopm1.gotcamel.predicates.Predicates.isFindFullMovieByMovieId;
import static com.github.gustavopm1.gotcamel.predicates.Predicates.isFindMovieById;
import static com.github.gustavopm1.gotcamel.predicates.Predicates.isFindMovieByName;
import static com.github.gustavopm1.gotcamel.predicates.Predicates.isFindMovieCastByMovieId;
import static com.github.gustavopm1.gotcamel.predicates.Predicates.isFindMovieCrewByMovieId;
import static com.github.gustavopm1.gotcamel.predicates.Predicates.isFindMovieKeywordsByMovieId;

@Component
@Slf4j
public class MovieApiRoute extends MainRouteBuilder {


    @Autowired
    @Setter
    private MovieSearchService movieSearchService;

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
    private MovieSearchByIdService movieSearchByIdService;

    @Autowired
    @Setter
    private MovieMarshaller marshaller;

    @Override
    public String getFrom() {
        return configuration.getRoutes().getInbound().getMovie();
    }

    @Override
    public String getRouteId() {
        return configuration.getIds().getMovie();
    }

    @Override
    public String getRouteName() {
        return configuration.getNames().getMovie();
    }

    @Override
    public void routeConfigure(RouteDefinition processor) {

        processor.setHeader("Test1", Builder.constant("Value1"))
                .setHeader("Test2", Builder.constant("Value2"))
                .log(LoggingLevel.INFO,log,getRouteId(), GotCamelConstants.ROUTE_START_KEY + " :: " + getRouteName())
                .unmarshal(marshaller)
                .log(LoggingLevel.INFO,log,getRouteId(), "Headers are ${headers}")
                .choice()
                    .when(isFindMovieByName())
                        .bean(movieSearchService,"getMovie").id("getMovieServiceBean")
                    .endChoice()

                    .when(isFindMovieById())
                        .bean(movieSearchByIdService,"getMovieById").id("getMovieByIdServiceBean")
                    .endChoice()

                    .when(isFindMovieCastByMovieId())
                        .bean(movieSearchByIdService,"getMovieById").id("getMovieServiceOnCastIdBean")
                        .bean(movieCastService,"getMovieCastById").id("getMovieCastByIdServiceBean")
                    .endChoice()

                    .when(isFindMovieCrewByMovieId())
                        .bean(movieSearchByIdService,"getMovieById").id("getMovieServiceOnCrewIdBean")
                        .bean(movieCrewService,"getMovieCrewById").id("getMovieCrewByIdServiceBean")
                    .endChoice()

                    .when(isFindMovieKeywordsByMovieId())
                        .bean(movieSearchByIdService,"getMovieById").id("getMovieServiceOnKeywordsIdBean")
                        .bean(movieKeywordsService,"getMovieKeywordsById").id("getMovieKeywordsByIdServiceBean")
                    .endChoice()

                    .when(isFindFullMovieByMovieId())
                        .bean(movieSearchByIdService,"getMovieById").id("getFullMovieServicedBean")
                        .bean(movieCastService,"getMovieCastById").id("getFullMovieCastByIdServiceBean")
                        .bean(movieCrewService,"getMovieCrewById").id("getFullMovieCrewByIdServiceBean")
                        .bean(movieKeywordsService,"getMovieKeywordsById").id("getFullMovieKeywordsByIdServiceBean")
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

        processor
            .onException(MovieNotFoundException.class)
                .handled(true)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        log.error("Error: Movie not found");
                        exchange.getOut().setBody(Response.<Movie>builder().found(false).build());
                        exchange.getIn().setBody(Response.<Movie>builder().found(false).build());
                    }
                })
             .onException(TooManyRequestsException.class)
                .handled(true)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        log.error("Error: Too many requests");
                        exchange.getOut().setBody(Response.<Movie>builder().found(false).build());
                        exchange.getIn().setBody(Response.<Movie>builder().found(false).build());
                    }
                })
                .marshal().json(JsonLibrary.Jackson,true)
                .convertBodyTo(String.class)
                .end();


    }
}
