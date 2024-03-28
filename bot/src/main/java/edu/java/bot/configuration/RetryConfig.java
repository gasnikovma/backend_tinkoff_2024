package edu.java.bot.configuration;

import edu.java.bot.exception.ServiceException;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Configuration
@SuppressWarnings("InnerTypeLast")
public class RetryConfig {

    public static class RetryIteration {
        private final int attempt;
        private final Retry.RetrySignal signal;

        public RetryIteration(Retry.RetrySignal retrySignal, int attempt) {
            this.attempt = attempt;
            this.signal = retrySignal;
        }

    }

    ApplicationConfig applicationConfig;

    @Autowired
    public RetryConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    private boolean shouldRetry(Throwable throwable) {
        if (throwable instanceof ServiceException) {
            int statusCode = ((ServiceException) throwable).getStatusCode();
            return applicationConfig.retry().statusCode().contains(statusCode);
        } else {
            return false;
        }
    }

    private Retry getConstantRetry() {
        return Retry.fixedDelay(
            applicationConfig.retry().attempts(),
            Duration.ofMillis(applicationConfig.retry().delay())
        ).filter(this::shouldRetry);

    }

    private Retry getExponentialRetry() {
        return Retry.backoff(applicationConfig.retry().attempts(), Duration.ofMillis(applicationConfig.retry().delay()))
            .filter(this::shouldRetry);
    }

    private Retry getLinearRetry() {
        int maxAttempts = applicationConfig.retry().attempts();
        Duration duration = Duration.ofMillis(applicationConfig.retry().delay());
        return Retry.from(
            retrySignalFlux -> retrySignalFlux.zipWith(Flux.range(1, maxAttempts), RetryIteration::new)
                .flatMap(it -> {

                    if (it.attempt < maxAttempts) {
                        return Mono.delay(duration.multipliedBy(it.attempt));
                    } else {
                        return Mono.error(it.signal.failure());
                    }
                })
        );
    }

    @Bean
    public Retry getRetry() {
        RetryType retryType = applicationConfig.retry().type();
        if (RetryType.CONSTANT.equals(retryType)) {
            return getConstantRetry();
        } else if (RetryType.EXPONENTIAL.equals(retryType)) {
            return getExponentialRetry();
        } else if (RetryType.LINEAR.equals(retryType)) {
            return getLinearRetry();
        }
        return null;
    }

}
