package edu.java.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;

@Configuration
public class SchedulerConfig {

    ApplicationConfig applicationConfig;

    @Autowired
    public SchedulerConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    @Bean
    public Duration interval() {
        return applicationConfig.scheduler().interval();
    }
}
