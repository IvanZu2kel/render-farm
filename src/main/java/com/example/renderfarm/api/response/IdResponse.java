package com.example.renderfarm.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class IdResponse {
    @JsonProperty("person_id")
    private long personId;
}
