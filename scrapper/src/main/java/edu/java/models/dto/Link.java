package edu.java.models.dto;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Link {
    private long id;
    private String uri;
    private OffsetDateTime lastCheck;
    private OffsetDateTime lastUpdate;
}
