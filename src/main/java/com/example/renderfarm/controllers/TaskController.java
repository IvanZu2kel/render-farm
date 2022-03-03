package com.example.renderfarm.controllers;

import com.example.renderfarm.api.response.*;
import com.example.renderfarm.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@Tag(name = "Контроллеры для работы с задачами")
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @Operation(summary = "Создание задачи", security = @SecurityRequirement(name = "default"))
    @PostMapping
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<DataResponse<TaskResponse>> createTask(Principal principal) {
        return new ResponseEntity<>(taskService.createTask(principal), HttpStatus.OK);
    }

    @Operation(summary = "Получение списка задач и их текущих статусов", security = @SecurityRequirement(name = "default"))
    @GetMapping
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<ListDataResponse<TaskResponse>> getAllTasks(Principal principal) {
        return new ResponseEntity<>(taskService.getAllTasks(principal), HttpStatus.OK);
    }

    @Operation(summary = "Получение истории выполнений задач", security = @SecurityRequirement(name = "default"))
    @GetMapping("/history")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<ListDataResponse<TaskHistoryResponse>> getAllHistory(Principal principal) {
        return new ResponseEntity<>(taskService.getAllHistory(principal), HttpStatus.OK);
    }
}
