package edu.java.bot.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record LinkUpdateRequest(
    @Min(1)
    long id,
    @NotBlank
    String url,
    @NotBlank
    String description,
    @NotEmpty
    List<Long> tgChatIds

) {
}
