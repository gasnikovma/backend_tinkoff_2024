package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BotBean {
    @Bean
    public TelegramBot telegramBot(ApplicationConfig applicationConfig) {
        return applicationConfig.telegramToken() != null ? new TelegramBot(applicationConfig.telegramToken()) : null;
    }

}
