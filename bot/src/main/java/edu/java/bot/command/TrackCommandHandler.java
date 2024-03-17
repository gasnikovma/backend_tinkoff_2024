package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TrackCommandHandler implements Command {
    private CommandUtils commandUtils;
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
        return commandUtils.trackLink(update);
    }
}
