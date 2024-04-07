package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.clients.ScrapperClient;
import edu.java.bot.models.response.LinkResponse;
import edu.java.bot.models.response.ListLinksResponse;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@SuppressWarnings({"MultipleStringLiterals", "MagicNumber"})
public class CommandUtils {
    @Autowired public void setScrapperClient(ScrapperClient scrapperClient) {
        this.scrapperClient = scrapperClient;
    }

    private ScrapperClient scrapperClient;

    public List<Command> getCommands() {
        return Arrays.asList(
            new StartCommandHandler(this),
            new HelpCommandHandler(this),
            new ListCommandHandler(this),
            new TrackCommandHandler(this),
            new UntrackCommandHandler(this)
        );
    }

    public String getAllCommands() {
        StringBuilder stringCommands = new StringBuilder();
        List<Command> s = getCommands();
        stringCommands.append("You can control me by sending these commands:").append("\n\n");
        s.stream().filter(it -> it.name().equals("/track") || it.name().equals("/untrack"))
            .forEach(it -> stringCommands.append(it.name()).append(" <URI>").append(" - ").append(it.descripton())
                .append("\n"));
        s.stream().filter(it -> !it.name().equals("/track") && !it.name().equals("/untrack"))
            .forEach(it -> stringCommands.append(it.name()).append(" - ").append(it.descripton()).append("\n"));

        return String.valueOf(stringCommands);

    }

    public SendMessage getLinks(Update update) {
        try {
            ListLinksResponse linkResponse = scrapperClient.getLinks(update.message().chat().id()).getBody();
            if (linkResponse.size() == 0) {
                return new SendMessage(update.message().chat().id(), "No links yet!");
            }
            StringBuilder subscribedLinksResponse = new StringBuilder();
            subscribedLinksResponse.append("List of tracked links:").append("\n");
            List<LinkResponse> linkResponses = linkResponse.linkResponses();
            for (LinkResponse link : linkResponses) {
                subscribedLinksResponse.append(link.uri()).append("\n");
            }
            return new SendMessage(update.message().chat().id(), subscribedLinksResponse.toString());
        } catch (Exception e) {
            return new SendMessage(update.message().chat().id(), "You are not registered!");
        }

    }

    public SendMessage registerChat(Update update) {
        try {
            scrapperClient.registerChat(update.message().chat().id());
            return new SendMessage(
                update.message().chat().id(),
                "This is link tracer bot! It helps to check updates on GitHub and StackOverFlow websites"
            );
        } catch (Exception e) {
            return new SendMessage(update.message().chat().id(), "You have already been registered!");
        }

    }

    public SendMessage trackLink(Update update) {
        List<String> request = Arrays.stream(update.message().text().split(" ")).toList();
        if (request.size() != 2) {
            return new SendMessage(update.message().chat().id(), "Invalid format! Use one argument <URI>");
        }
        if (!validateLink(request.get(1))) {
            return new SendMessage(
                update.message().chat().id(),
                " This link is incorrect. Valid format: https//github.com/* or https://stackoverflow.com/questions/*"
            );
        }
        try {
            scrapperClient.addLink(update.message().chat().id(), request.get(1));
            return new SendMessage(update.message().chat().id(), request.get(1) + " is now tracked!");
        } catch (Exception e) {
            return new SendMessage(update.message().chat().id(), request.get(1) + " was already tracked!");
        }

    }

    public SendMessage untrackLink(Update update) {
        List<String> request = Arrays.stream(update.message().text().split(" ")).toList();
        if (request.size() != 2) {
            return new SendMessage(update.message().chat().id(), "Invalid format! Use one argument <URI>");
        }
        if (!validateLink(request.get(1))) {
            return new SendMessage(
                update.message().chat().id(),
                " This link is incorrect. Valid format: https//github.com/* or https://stackoverflow.com/questions/*"
            );
        }
        try {
            scrapperClient.removeLink(update.message().chat().id(), request.get(1));
            return new SendMessage(update.message().chat().id(), request.get(1) + " is now untracked!");
        } catch (Exception e) {
            return new SendMessage(update.message().chat().id(), request.get(1) + " was already untracked!");
        }

    }

    public boolean validateLink(String uri) {
        if (uri.startsWith("https://github.com/")) {
            String[] uriParts = uri.split("/");
            return uriParts.length == 5 && !uriParts[3].isEmpty() && !uriParts[4].isEmpty();
        }
        if (uri.startsWith("https://stackoverflow.com/questions/")) {
            String[] uriParts = uri.split("/");
            return uriParts.length == 6
                && uriParts[4].matches("-?\\d+") && !uriParts[4].isEmpty()
                && !uriParts[5].isEmpty();
        }
        return false;
    }

}
