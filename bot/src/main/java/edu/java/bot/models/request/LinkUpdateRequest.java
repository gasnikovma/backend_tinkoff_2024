package edu.java.bot.models.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

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
