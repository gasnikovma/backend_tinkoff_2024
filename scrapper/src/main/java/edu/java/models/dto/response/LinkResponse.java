package edu.java.models.dto.response;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record LinkResponse(
    @Min(1)
    Long id,
    @NotBlank
    String uri
) {
}
