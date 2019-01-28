package com.github.gustavopm1.gotcamel.services.experimental;

import com.github.gustavopm1.gotcamel.models.experimental.ExperimentalType1;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.support.SynchronizationAdapter;

@Slf4j
public class BulkProcessExperimentalCallback extends SynchronizationAdapter {

    @Override
    public void onComplete(Exchange exchange) {

        log.info("On Complete of {}",exchange.getIn().getBody());

        //Here I can do something just by using it's ID for example
        //((ExperimentalType1)exchange.getIn().getBody()).getId()

        super.onComplete(exchange);
    }

    @Override
    public void onDone(Exchange exchange) {

        log.info("On Done of {}",exchange.getIn().getBody());

        super.onDone(exchange);
    }

    @Override
    public void onFailure(Exchange exchange) {

        log.info("On Failure of {}",exchange.getIn().getBody());

        super.onFailure(exchange);
    }
}
