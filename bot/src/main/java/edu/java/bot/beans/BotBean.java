package edu.java.bot.beans;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.configuration.ApplicationConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotBean {
    @Bean
    public TelegramBot telegramBot(ApplicationConfig applicationConfig) {
        return applicationConfig.telegramToken() != null ? new TelegramBot(applicationConfig.telegramToken()) : null;
    }

}
