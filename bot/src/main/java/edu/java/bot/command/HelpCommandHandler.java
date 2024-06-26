package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HelpCommandHandler implements Command {

    private CommandUtils commandUtils;

    @Override
    public String name() {
        return "/help";
    }

    @Override
    public String descripton() {
        return "print commands";
    }

    @Override
    public SendMessage handle(Update update) {

        return new SendMessage(update.message().chat().id(), commandUtils.getAllCommands());
    }

}
