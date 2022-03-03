package com.example.renderfarm.model;

import com.example.renderfarm.model.enums.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity(name = "history")
@Accessors(chain = true)
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Task task;

    private Instant date;

    private Status status;
}
