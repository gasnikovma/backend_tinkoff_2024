package edu.java.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OwnerResponse(
    @JsonProperty("login")
    String nickname
) {

}
