package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UntrackCommandHandler implements Command {

    private CommandUtils commandUtils;

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
        return commandUtils.untrackLink(update);
    }
}
