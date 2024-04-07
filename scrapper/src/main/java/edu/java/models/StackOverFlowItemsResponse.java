package edu.java.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record StackOverFlowItemsResponse(
    @JsonProperty("question_id")
    Long questionId,
    @JsonProperty("last_activity_date")
    OffsetDateTime lastActivityDate,
    @JsonProperty("creation_date")
    OffsetDateTime creationDate,
    @JsonProperty("last_edit_date")
    OffsetDateTime lastEditDate
) {
}
