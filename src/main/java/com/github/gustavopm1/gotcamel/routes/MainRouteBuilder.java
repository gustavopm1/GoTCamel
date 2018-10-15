package com.github.gustavopm1.gotcamel.routes;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.spring.spi.ApplicationContextRegistry;

import java.time.Duration;
import java.time.LocalDateTime;


@Slf4j
public abstract class MainRouteBuilder extends RouteBuilder {

    protected static final String ROUTE_START_KEY = "SOR";
    protected static final String ROUTE_END_KEY = "EOR";
    protected static final String ROUTE_DURATION = "DOR";

    public abstract void routeConfigure(RouteDefinition processor);
    public abstract void routeExceptions(RouteDefinition processor);
    public abstract String getFrom();
    public abstract String getRouteId();

    @Override
    public void configure(){

        RouteDefinition processor = from(getFrom()).routeId(getRouteId()).process(this::processRouteStart);
        this.routeExceptions(processor);
        this.routeConfigure(processor);

        //Print the end of the process with the timer marking how much it took to process the queue
        processor.onCompletion().process(this::processRouteEnd).process(this::logDuration).log("Took ${in.header."+ROUTE_DURATION+"} to process Exchange ${exchangeId}");

        //Handle the generic exception
       /* processor
            .onException(Exception.class)
            .handled(true)
            .log("Ocorreu um erro ao tratar o dado da fila "+getRouteId()+"")
        .end();*/
    }



    private void processRouteStart(Exchange exchange){ exchange.getIn().setHeader(ROUTE_START_KEY, LocalDateTime.now()); }

    private void processRouteEnd(Exchange exchange){ exchange.getIn().setHeader(ROUTE_END_KEY, LocalDateTime.now()); }

    private void logDuration(Exchange exchange){
        final LocalDateTime start = (LocalDateTime)exchange.getIn().getHeaders().get(ROUTE_START_KEY);
        final LocalDateTime end = (LocalDateTime)exchange.getIn().getHeaders().get(ROUTE_END_KEY);
        exchange.getIn().setHeader(ROUTE_DURATION, Duration.between(start,end).toString());
    }

}
