package edu.java.configuration;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CounterConfig {
    @Bean
    public Counter counter(MeterRegistry meterRegistry) {
        return meterRegistry.counter("messages_processed");

    }
}
