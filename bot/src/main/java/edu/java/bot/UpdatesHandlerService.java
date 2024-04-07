package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.models.request.LinkUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdatesHandlerService {
    private final TelegramBot bot;

    public void handleUpdates(LinkUpdateRequest linkUpdateRequest) {
        List<Long> chats = linkUpdateRequest.getTgChatIds();
        log.info(chats.toString());
        for (Long chatId : chats) {
            bot.execute(new SendMessage(chatId, linkUpdateRequest.getDescription() + linkUpdateRequest.getUri()));
        }
    }
}
