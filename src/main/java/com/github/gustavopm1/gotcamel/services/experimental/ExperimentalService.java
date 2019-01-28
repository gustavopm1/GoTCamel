package com.github.gustavopm1.gotcamel.services.experimental;

import com.github.gustavopm1.gotcamel.configuration.GotCamelConfiguration;
import com.github.gustavopm1.gotcamel.models.experimental.ExperimentalType1;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Body;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@Service
@Slf4j
public class ExperimentalService {

    @Autowired
    @Setter
    protected ProducerTemplate template;

    @Autowired
    @Setter
    protected GotCamelConfiguration configuration;

    private AtomicLong counter = new AtomicLong(0);

    public void experimentSend(@Body String data){

        log.info("\n\n Experimenting a string flavour\n{}\n\n",data);

        List<ExperimentalType1> expList = new ArrayList<>();

        for(int x = 0; x < 10; x++) {
            expList.add(ExperimentalType1.builder()
                    .id(counter.getAndIncrement())
                    .name(UUID.randomUUID().toString())
                    .build()
            );
        }

        BulkProcessExperimentalCallback bulkCallback = new BulkProcessExperimentalCallback();

        expList.forEach(
                experimentalType1 -> {
                    log.info("\n\nExperiment this sir -> {}\n\n",experimentalType1);
                    template
                        .asyncCallback(configuration.getRoutes().getOutbound().getExperimentalPattern(),
                                new Processor() {
                                    @Override
                                    public void process(Exchange exchange) throws Exception {
                                        exchange.getIn().setBody(experimentalType1);
                                    }
                                }, bulkCallback);
                }
        );

    }

    public void experimentReceive(@Body ExperimentalType1 value){
        log.info("\n\n Experimenting our flavor of\n{}\n\n",value);
        try {
            Thread.sleep( new Random().ints(1,5).findFirst().getAsInt() * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
