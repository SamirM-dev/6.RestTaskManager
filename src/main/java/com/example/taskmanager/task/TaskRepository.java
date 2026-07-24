package com.example.taskmanager.task;

import com.example.taskmanager.enums.TaskPriority;
import com.example.taskmanager.enums.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    public Page<Task> getByUser_Id(Long id, Pageable pageable);
    @Query("SELECT t FROM Task t WHERE(:status IS NULL OR t.status=:status) AND (:priority IS NULL OR t.priority=:priority)")
    public Page<Task> findByFilters(@Param("status") TaskStatus status,@Param("priority") TaskPriority priority, Pageable pageable);
}
