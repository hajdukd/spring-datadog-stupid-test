package com.sample.springbootappwithmetrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;

import java.time.Instant;

@SpringBootApplication
public class SpringBootAppWithMetricsApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(SpringBootAppWithMetricsApplication.class);

    @Autowired
    private ConfigurableEnvironment env;

    @Autowired
    private MeterRegistry meterRegistry;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAppWithMetricsApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("App started");
        Instant end = Instant.now().plusSeconds(1200); // run for 2 minutes
        final Counter counter = meterRegistry.counter("myapp.metric2");
        final Integer _10_SECONDS = 10000;

        //final PrimitiveIterator.OfLong waitTimes = new Random().longs(10, 100).iterator();
        //final PrimitiveIterator.OfInt countValues = new Random().ints(1, 100).iterator();
        while(Instant.now().isBefore(end)) {
            //counter.increment();
            Thread.sleep(_10_SECONDS);
        }
    }
}
