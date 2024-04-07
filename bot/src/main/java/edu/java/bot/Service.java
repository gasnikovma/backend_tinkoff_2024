package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.command.Command;
import edu.java.bot.command.CommandUtils;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
@Slf4j
public class Service {

    private final TelegramBot bot;

    private final CommandUtils commandUtils;

    @Autowired
    private Service(TelegramBot telegramBot, CommandUtils commandUtils) {
        this.bot = telegramBot;
        this.commandUtils = commandUtils;
    }

    public void handleUpdates(Update update) {
        boolean isValidCommand = false;
        if (update.message() != null) {
            log.info(update.message().text());
            for (Command command : commandUtils.getCommands()) {
                if (update.message().text().split(" ")[0].equals(command.name())) {
                    isValidCommand = true;
                    bot.execute(command.handle(update));

                }
            }
            if (!isValidCommand) {
                bot.execute(new SendMessage(update.message().chat().id(), "This command is not valid!"));
            }
        }

    }

    public void setMyCommands() {
        List<BotCommand> botCommands = commandUtils.getCommands().stream()
            .map(command -> new BotCommand(command.name(), command.descripton())).toList();

        SetMyCommands setMyCommands = new SetMyCommands(botCommands.toArray(new BotCommand[0]));
        bot.execute(setMyCommands);
    }
}
