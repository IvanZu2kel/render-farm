package com.example.renderfarm.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ListDataResponse<T extends Dto>{
    private long timestamp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<T> data;
}
