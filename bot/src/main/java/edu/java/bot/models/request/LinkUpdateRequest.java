package edu.java.bot.models.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class LinkUpdateRequest {
    @Min(1)
    long id;
    @NotBlank
    String uri;
    @NotBlank
    String description;
    @NotEmpty
    List<Long> tgChatIds;

}
