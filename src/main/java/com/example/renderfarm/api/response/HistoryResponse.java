package com.example.renderfarm.api.response;

import com.example.renderfarm.model.enums.Status;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;

@Data
@Accessors(chain = true)
public class HistoryResponse {
    private Status status;
    private Instant date;
}
