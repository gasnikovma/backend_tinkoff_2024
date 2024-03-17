package edu.java.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class Link {
    private long id;
    private String name;
    private OffsetDateTime lastCheck;
    private OffsetDateTime lastUpdate;
}
