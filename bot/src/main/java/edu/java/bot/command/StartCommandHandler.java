package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StartCommandHandler implements Command {

    private CommandUtils commandUtils;

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
        return commandUtils.registerChat(update);
    }
}
