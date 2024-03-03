package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class TrackCommandHandler implements Command {
    @Override
    public String name() {
        return "/track";
    }

    @Override
    public String descripton() {
        return "start tracking this url";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), "Please,enter the link you want to track");
    }
}
