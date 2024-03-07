package edu.java.models.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AddLinkRequest(
    @NotBlank
    String uri
) {
}
