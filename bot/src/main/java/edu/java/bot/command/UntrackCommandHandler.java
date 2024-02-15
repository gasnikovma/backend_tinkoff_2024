package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class UntrackCommandHandler implements Command {
    @Override
    public String name() {
        return "/untrack";
    }

    @Override
    public String descripton() {
        return "stop tracking this url";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(
            update.message().chat().id(),
            "Enter the link from which you no longer want to receive updates"
        );
    }
}
