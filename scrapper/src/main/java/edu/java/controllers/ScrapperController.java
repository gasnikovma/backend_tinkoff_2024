package edu.java.controllers;

import edu.java.models.dto.Link;
import edu.java.models.dto.request.AddLinkRequest;
import edu.java.models.dto.request.RemoveLinkRequest;
import edu.java.models.dto.response.ApiErrorResponse;
import edu.java.models.dto.response.LinkResponse;
import edu.java.models.dto.response.ListLinksResponse;
import edu.java.service.LinkService;
import edu.java.service.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("scrapper")
@RequiredArgsConstructor
@Slf4j
public class ScrapperController {

    private final LinkService linkService;
    private final RegisterService registerService;

    @Operation(summary = "Зарегистровать чат")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Чат зарегистрирован"),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class))),
        @ApiResponse(responseCode = "406",
                     description = "Чат с заданным id уже существует",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class)))

    })
    @PostMapping("/tg-chat/{id}")
    public String  registerChat(@PathVariable long id) {

        registerService.reigster(id);
        return "Chat is registered!";
    }

    @Operation(summary = "Удалить чат")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Чат успешно удален"),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class))),
        @ApiResponse(responseCode = "404",
                     description = "Чат с заданным id не существует",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class)))

    })
    @DeleteMapping("/tg-chat/{id}")
    public String deleteChat(@PathVariable long id) {
        registerService.unregister(id);
        return "Chat is successfully deleted";
    }

    @Operation(summary = "Получить все отслеживаемые ссылки")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                     description = "Ссылки успешно получены",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ListLinksResponse.class))),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class))),
        @ApiResponse(responseCode = "406",
                     description = "Чат с заданным id уже существует",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class)))

    })

    @GetMapping("/links")
    ListLinksResponse getLinks(@RequestHeader("Tg-Chat-Id") long chatId) {
        List<Link> links = linkService.allLinks(chatId);
        List<LinkResponse> linkResponses = new ArrayList<>();
        for (int i = 0; i < links.size(); i++) {
            linkResponses.add(new LinkResponse(links.get(i).getId(), links.get(i).getName()));
        }
        return new ListLinksResponse(linkResponses, links.size());

    }

    @Operation(summary = "Добавить отслеживание ссылки")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                     description = "Ссылка успешно добавлена",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = LinkResponse.class))),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class))),
        @ApiResponse(responseCode = "404",
                     description = "Чат с заданным id не существует",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class)))

    })
    @PostMapping("/links")
    public LinkResponse addLink(
        @RequestHeader("Tg-Chat-Id")
        long chatId, @RequestBody
    AddLinkRequest addLinkRequest
    ) {
        log.info("ne zahodit(");
        Link link = linkService.add(chatId, URI.create(addLinkRequest.uri()));
        return new LinkResponse(link.getId(), link.getName());
    }

    @Operation(summary = "Убрать отслеживание ссылки")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                     description = "Ссылка успешно убрана",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = LinkResponse.class))),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class))),
        @ApiResponse(responseCode = "404",
                     description = "Ссылка не найдена",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class)))

    })

    @DeleteMapping("/links")
    public LinkResponse removeLink(
        @RequestHeader(name = "Tg-Chat-Id") long chatId, @RequestBody
    RemoveLinkRequest removeLinkRequest
    ) {
        Link link = linkService.remove(chatId, URI.create(removeLinkRequest.uri()));
        return new LinkResponse(link.getId(), link.getName());
    }

}
