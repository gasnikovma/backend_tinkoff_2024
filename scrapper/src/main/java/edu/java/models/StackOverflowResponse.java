package edu.java.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record StackOverflowResponse(
    @JsonProperty("items")
    List<StackOverFlowItemsResponse>itemsResponses
) {

}
