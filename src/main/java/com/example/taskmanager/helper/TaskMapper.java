package com.example.taskmanager.helper;

import com.example.taskmanager.task.Task;
import com.example.taskmanager.task.TaskCreateRequest;
import com.example.taskmanager.task.TaskResponse;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskResponse toResponse(Task task){
        TaskResponse response=new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        response.setPriority(task.getPriority());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());
        response.setUserId(task.getUser().getId());
        return response;
    }
}
