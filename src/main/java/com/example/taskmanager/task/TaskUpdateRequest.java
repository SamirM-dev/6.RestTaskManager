package com.example.taskmanager.task;

import com.example.taskmanager.validation.TaskPriorityValid;
import com.example.taskmanager.validation.TaskStatusValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskUpdateRequest {
    @NotBlank(message = "Title should be entered")
    private String title;
    @NotBlank(message = "Description should be entered")
    @Size(max=500,message = "Description should be less than 500 symbols")
    private String description;
    @NotBlank(message = "Status should be entered")
    @TaskStatusValid(message = "Not valid status for task")
    private String status;
    @NotBlank(message = "Priority should be entered")
    @TaskPriorityValid(message = "Not valid priority for task")
    private String priority;
}
