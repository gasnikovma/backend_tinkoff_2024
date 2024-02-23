package edu.java.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record StackOverflowResponse(
    @JsonProperty("items")
<<<<<<< HEAD
    List<StackOverFlowItemsResponse> itemsResponses
=======
    List<StackOverFlowItemsResponse>itemsResponses
>>>>>>> 8a3421a (hw2_first)
) {

}
