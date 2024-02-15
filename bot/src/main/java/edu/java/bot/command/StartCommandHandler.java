package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class StartCommandHandler implements Command {

    @Override
    public String name() {
        return "/start";
    }

    @Override
    public String descripton() {
        return "register user";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), "User has been successfully registered");
    }
}
