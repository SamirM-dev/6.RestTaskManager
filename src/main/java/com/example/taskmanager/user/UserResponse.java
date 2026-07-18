package com.example.taskmanager.user;

import com.example.taskmanager.task.Task;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private Long id;
    private String name;
    private List<Task> tasks;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
