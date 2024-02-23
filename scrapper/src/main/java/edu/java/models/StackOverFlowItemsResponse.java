package edu.java.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StackOverFlowItemsResponse(
    @JsonProperty("question_id")
    Long questionId,
    @JsonProperty("last_activity_date")
    Long lastActivityDate,
    @JsonProperty("creation_date")
    Long creationDate,
    @JsonProperty("last_edit_date")
    Long lastEditDate
) {
}
