package com.example.renderfarm.service;

import com.example.renderfarm.api.response.*;
import com.example.renderfarm.model.History;
import com.example.renderfarm.model.Person;
import com.example.renderfarm.model.Task;
import com.example.renderfarm.model.enums.Status;
import com.example.renderfarm.repo.HistoryRepository;
import com.example.renderfarm.repo.PersonRepository;
import com.example.renderfarm.repo.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;

@Service
@EnableScheduling
@RequiredArgsConstructor
public class TaskService {
    private final PersonRepository personRepository;
    private final TaskRepository taskRepository;
    private final HistoryRepository historyRepository;

    public DataResponse<TaskResponse> createTask(Principal principal) {
        Optional<Person> person = personRepository.findByEmail(principal.getName());
        Task task = new Task();
        task
                .setPerson(person.orElseThrow())
                .setStatus(Status.RENDERING);
        taskRepository.save(task);
        createHistory(task, Status.RENDERING);
        return new DataResponse<TaskResponse>()
                .setTimestamp(Instant.now().getEpochSecond())
                .setData(new TaskResponse()
                        .setId(task.getId())
                        .setStatus(task.getStatus()));
    }

    public ListDataResponse<TaskResponse> getAllTasks(Principal principal) {
        List<Task> taskList = taskRepository.findAllByPersonEmail(principal.getName());
        return getListDataResponse(taskList);
    }

    public ListDataResponse<TaskHistoryResponse> getAllHistory(Principal principal) {
        List<Task> taskList = taskRepository.findAllByPersonEmail(principal.getName());
        ListDataResponse<TaskHistoryResponse> listDataResponse = new ListDataResponse<>();
        List<TaskHistoryResponse> taskHistoryResponses = new ArrayList<>();
        for (Task task : taskList) {
            TaskHistoryResponse taskHistoryResponse = new TaskHistoryResponse();
            taskHistoryResponse
                    .setTaskId(task.getId())
                    .setHistory(getHistory(task));
            taskHistoryResponses.add(taskHistoryResponse);
        }
        listDataResponse
                .setTimestamp(Instant.now().getEpochSecond())
                .setData(taskHistoryResponses);
        return listDataResponse;
    }

    private List<HistoryResponse> getHistory(Task task) {
        List<History> history = taskRepository.getByTaskId(task.getId());
        List<HistoryResponse> historyResponses = new ArrayList<>();
        for (History h : history) {
            HistoryResponse historyResponse = new HistoryResponse();
            historyResponse
                    .setStatus(h.getStatus())
                    .setDate(h.getDate());
            historyResponses.add(historyResponse);
        }
        return historyResponses;
    }

    private ListDataResponse<TaskResponse> getListDataResponse(List<Task> taskList) {
        ListDataResponse<TaskResponse> listDataResponse = new ListDataResponse<>();
        listDataResponse.setTimestamp(Instant.now().getEpochSecond());
        List<TaskResponse> list = new ArrayList<>();
        for (Task task : taskList) {
            TaskResponse taskResponse = new TaskResponse()
                    .setId(task.getId())
                    .setStatus(task.getStatus());
            list.add(taskResponse);
        }
        listDataResponse.setData(list);
        return listDataResponse;
    }

    /**
     * очередь из объектов которые нужно рендерить
     */
    @Scheduled(fixedDelay = 5_000)
    private void startWorkingTask() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        List<Task> tasks = taskRepository.findAllByStatusRendering();
        Runnable r = () -> {
            for (Task task : tasks) {
                task.setStatus(Status.IN_PROCESS);
                taskRepository.save(task);
                createHistory(task, Status.IN_PROCESS);
                try {
                    Thread.sleep(60_000 + (long) (Math.random() * 240_000));
                    task.setStatus(Status.COMPLETE);
                    taskRepository.save(task);
                    createHistory(task, Status.COMPLETE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        forkJoinPool.submit(r);
    }

    private void createHistory(Task task, Status status) {
        History history = new History();
        history
                .setTask(task)
                .setDate(Instant.now())
                .setStatus(status);
        historyRepository.save(history);
    }
}
