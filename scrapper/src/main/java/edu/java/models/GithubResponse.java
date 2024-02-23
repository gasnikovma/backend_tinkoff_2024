package edu.java.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GithubResponse(
    @JsonProperty("owner")
    OwnerResponse ownerResponse,
    @JsonProperty("name")
    String repositoryName,
    @JsonProperty("created_at")
    OffsetDateTime created,
    @JsonProperty("updated_at")
    OffsetDateTime updated,
    @JsonProperty("pushed_at")
    OffsetDateTime pushed
) {

}
