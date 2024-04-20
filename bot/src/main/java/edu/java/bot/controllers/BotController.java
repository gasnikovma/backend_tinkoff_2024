package edu.java.bot.controllers;

import edu.java.bot.UpdatesHandlerService;
import edu.java.bot.models.request.LinkUpdateRequest;
import edu.java.bot.models.response.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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

    private final UpdatesHandlerService service;

    @Operation(summary = "Отправить обновление")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Обновление обработано"),

        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class)))})

    @PostMapping(path = "/updates")
    public ResponseEntity<Void> update(@RequestBody @Valid LinkUpdateRequest linkUpdateRequest) {
        service.handleUpdates(linkUpdateRequest);
        return ResponseEntity.ok().build();
    }

}
