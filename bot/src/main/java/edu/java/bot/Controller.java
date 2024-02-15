package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Controller
public class Controller {

    private final Service telegramBotService;
    private final TelegramBot bot;

    @Autowired
    public Controller(Service service, TelegramBot bot) {
        this.telegramBotService = service;
        this.bot = bot;
        this.run();
    }

    public void run() {
        telegramBotService.setMyCommands();
        bot.setUpdatesListener(list -> {
            list.forEach(telegramBotService::handleUpdates);

            return UpdatesListener.CONFIRMED_UPDATES_ALL;

        });
    }
}
