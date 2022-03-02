package com.example.renderfarm.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DataResponse<T extends Dto> {
    private long timestamp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
}
