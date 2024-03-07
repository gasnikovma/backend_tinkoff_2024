package edu.java.bot.models.request;

import jakarta.validation.constraints.NotBlank;

public record RemoveLinkRequest(
    @NotBlank
    String uri
)  {
}
