package edu.java.bot.models.response;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record ListLinksResponse(
    @NotEmpty
    List<LinkResponse> linkResponses,
    int size
) {
}
