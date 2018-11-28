package com.github.gustavopm1.gotcamel.routes;

import com.github.gustavopm1.gotcamel.GotCamelConstants;
import com.github.gustavopm1.gotcamel.configuration.GotCamelConfiguration;
import io.micrometer.core.instrument.Metrics;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalDateTime;


@Slf4j
public abstract class MainRouteBuilder extends RouteBuilder {

    public abstract String getFrom();
    public abstract String getRouteId();
    public abstract String getRouteName();

    public abstract void routeConfigure(RouteDefinition processor);
    public abstract void routeExceptions(RouteDefinition processor);

    @Autowired
    @Setter
    protected GotCamelConfiguration configuration;

    @Override
    public void configure(){

        RouteDefinition processor = from(getFrom())
                                    .routeId(getRouteId())
                                    .process(this::testMetrics)
                                    .process(this::processRouteStart);

        //Print the end of the process with the timer marking how much it took to process the queue
        processor
            .onCompletion()
                .process(this::processRouteEnd)
                .process(this::logDuration)
                .log(LoggingLevel.INFO,log,getRouteId(),"Took ${in.header."+ GotCamelConstants.ROUTE_DURATION+"} to process ${in.header."+GotCamelConstants.ROUTE_NAME+"}")
                .log(LoggingLevel.TRACE,log,getRouteId(),"IN.BODY: ${in.body} - OUT.BODY: ${out.body} - IN.HEADERS: ${in.headers}")
            .end()
        ;

        this.routeExceptions(processor);
        this.routeConfigure(processor);
    }

    private void processRouteStart(Exchange exchange){
        exchange.getIn().setHeader(GotCamelConstants.ROUTE_START_KEY, LocalDateTime.now());
        exchange.getIn().setHeader(GotCamelConstants.ROUTE_ID, getRouteId());
        exchange.getIn().setHeader(GotCamelConstants.ROUTE_NAME, getRouteName());
    }

    private void processRouteEnd(Exchange exchange){ exchange.getIn().setHeader(GotCamelConstants.ROUTE_END_KEY, LocalDateTime.now()); }

    private void logDuration(Exchange exchange){
        final LocalDateTime start = (LocalDateTime)exchange.getIn().getHeaders().get(GotCamelConstants.ROUTE_START_KEY);
        final LocalDateTime end = (LocalDateTime)exchange.getIn().getHeaders().get(GotCamelConstants.ROUTE_END_KEY);
        if(start == null || end == null)
            exchange.getIn().setHeader(GotCamelConstants.ROUTE_DURATION, 0);
        else
            exchange.getIn().setHeader(GotCamelConstants.ROUTE_DURATION, Duration.between(start,end).toString());
    }

    private void testMetrics(Exchange exchange) {
        Metrics.counter("gotcamel.count.router", "result", "success").increment();
    }

}
