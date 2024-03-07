package edu.java.models.dto.response;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record ListLinksResponse(
    @NotEmpty
    List<LinkResponse> linkResponses,
    int size
) {
}
