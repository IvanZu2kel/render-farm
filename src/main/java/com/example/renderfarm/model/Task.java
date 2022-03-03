package com.example.renderfarm.model;

import com.example.renderfarm.model.enums.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "tasks")
@Accessors(chain = true)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Status status;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
}
