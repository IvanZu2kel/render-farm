package com.example.renderfarm.api.response;

import com.example.renderfarm.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskResponse implements Dto{
    private long id;
    private Status status;
    @JsonProperty("start_work")
    private Instant startWork;
    @JsonProperty("finish_work")
    private Instant finishWork;
}
