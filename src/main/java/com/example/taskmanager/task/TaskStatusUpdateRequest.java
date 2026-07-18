package com.example.taskmanager.task;

import com.example.taskmanager.validation.TaskStatusValid;
import jakarta.validation.constraints.NotBlank;

public class TaskStatusUpdateRequest {
    @NotBlank(message = "Status should be entered")
    @TaskStatusValid(message = "Status is not valid for task")
    private String status;
}
