package com.example.renderfarm.repo;

import com.example.renderfarm.model.History;
import com.example.renderfarm.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("select t from tasks t where t.person.email = :email")
    List<Task> findAllByPersonEmail(String email);

    @Query("select t from tasks t where t.status = 'RENDERING'")
    List<Task> findAllByStatusRendering();

    @Query("select h from history h where h.task.id = :id")
    List<History> getByTaskId(long id);
}
