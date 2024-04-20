package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Controller
public class Executor {

    private final Service telegraBotService;
    private final TelegramBot bot;

    @Autowired
    public Executor(Service service, TelegramBot bot) {
        this.telegraBotService = service;
        this.bot = bot;
        this.run();
    }

    public void run() {
        telegraBotService.setMyCommands();
        bot.setUpdatesListener(list -> {
            list.forEach(telegraBotService::handleUpdates);

            return UpdatesListener.CONFIRMED_UPDATES_ALL;

        });
    }
}
