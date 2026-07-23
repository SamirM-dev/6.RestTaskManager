package com.example.taskmanager.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponse {
    private Long id;
    private String text;
    private String author;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("task_id")
    private Long taskId;
}
