package edu.java.configuration;

<<<<<<< HEAD
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;
>>>>>>> 8a3421a (hw2_first)

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
