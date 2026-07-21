package com.example.taskmanager.task;

import com.example.taskmanager.enums.TaskPriority;
import com.example.taskmanager.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    public Page<Task> getByUser_Id(Long id, Pageable pageable);
    public Page<Task> getByStatusAndPriority(TaskStatus status, TaskPriority priority,Pageable pageable);
}
