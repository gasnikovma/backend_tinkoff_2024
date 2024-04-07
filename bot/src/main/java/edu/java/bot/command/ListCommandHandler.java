package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ListCommandHandler implements Command {

    private CommandUtils commandUtils;

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
        return commandUtils.getLinks(update);

    }
}
