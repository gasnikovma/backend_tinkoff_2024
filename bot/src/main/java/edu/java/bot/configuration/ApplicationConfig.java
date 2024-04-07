package edu.java.bot.configuration;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotEmpty
    String telegramToken,
    Retry retry
) {
    public record Retry(
        @NotNull
        RetryType type,
        @NotEmpty
        List<Integer> statusCode,
        @NotNull
        long delay,
        @NotNull
        int attempts

    ){}

}
