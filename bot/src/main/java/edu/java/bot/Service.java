package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import edu.java.bot.command.Command;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import static edu.java.bot.command.CommandUtils.getCommands;

@org.springframework.stereotype.Service
public class Service {

    private final TelegramBot bot;

    @Autowired
    private Service(TelegramBot telegramBot) {
        this.bot = telegramBot;
    }

    public void handleUpdates(Update update) {
        boolean isValidCommand = false;
        if (update.message() != null) {
            for (Command command : getCommands()) {
                if (update.message().text().equals(command.name())) {
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
        List<BotCommand> botCommands = getCommands().stream()
            .map(command -> new BotCommand(command.name(), command.descripton())).toList();

        SetMyCommands setMyCommands = new SetMyCommands(botCommands.toArray(new BotCommand[0]));
        bot.execute(setMyCommands);
    }
}
