package edu.java.bot.controllers;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.models.request.LinkUpdateRequest;
import edu.java.bot.models.response.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("bot")
@AllArgsConstructor
public class BotController {

    private final TelegramBot bot;

    @Operation(summary = "Отправить обновление")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Обновление обработано"),

        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class)))})

    @PostMapping(path = "/updates")
    public ResponseEntity<Void> update(@RequestBody @Valid LinkUpdateRequest linkUpdateRequest) {
        List<Long> chats = linkUpdateRequest.tgChatIds();
        log.info(chats.toString());
        for (Long chatId : chats) {
            bot.execute(new SendMessage(chatId, linkUpdateRequest.description() + linkUpdateRequest.uri()));
        }
        return ResponseEntity.ok().build();
    }

}
