package com.example.taskmanager.task;

import com.example.taskmanager.validation.TaskPriorityValid;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
class TaskCreateRequest {
    @NotBlank(message = "Title must be entered")
    private String title;
    @NotBlank(message = "Description must be entered")
    @Size(max=500,message = "Description should not be more than 500 symbols")
    private String description;
    @NotBlank(message = "Priority must be entered")
    @TaskPriorityValid(message = "Priority is not valid")
    private String priority;
    @NotBlank(message = "User ID must be entered")
    @Min(value = 1,message = "User ID can not me less than 1")
    @JsonProperty("user_id")
    private Long userId;
}
