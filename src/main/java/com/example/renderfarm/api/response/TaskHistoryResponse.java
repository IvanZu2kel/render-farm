package com.example.renderfarm.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class TaskHistoryResponse implements Dto{
    @JsonProperty("task_id")
    private long taskId;
    private List<HistoryResponse> history;
}
