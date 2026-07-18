package com.example.taskmanager.comment;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class CommentResponse {
    private String id;
    private String text;
    private String author;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("task_id")
    private Long taskId;
}
