package edu.java.bot.command;

import java.util.Arrays;
import java.util.List;

public class CommandUtils {
    private CommandUtils() {

    }

    public static List<Command> getCommands() {
        return Arrays.asList(
            new StartCommandHandler(),
            new HelpCommandHandler(),
            new ListCommandHandler(),
            new TrackCommandHandler(),
            new UntrackCommandHandler()
        );
    }

    public static String getAllCommands() {
        StringBuilder stringCommands = new StringBuilder();
        List<Command> s = CommandUtils.getCommands();
        stringCommands.append("You can control me by sending these commands:").append("\n\n");
        s.forEach(it -> stringCommands.append(it.name()).append(" - ").append(it.descripton()).append("\n"));
        return String.valueOf(stringCommands);

    }
}
