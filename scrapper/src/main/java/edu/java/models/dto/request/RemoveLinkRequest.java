package edu.java.models.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RemoveLinkRequest(
    @NotBlank
    String uri
)  {
}
