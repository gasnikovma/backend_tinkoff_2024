package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class ListCommandHandler implements Command {
    @Override
    public String name() {
        return "/list";
    }

    @Override
    public String descripton() {
        return "show the list of tracked links";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), "empty yet");
    }
}
