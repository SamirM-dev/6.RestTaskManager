package com.example.taskmanager.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.extern.java.Log;

@Data
public class CommentCreateRequest {
    @NotBlank(message = "Test must be entered")
    @Size(max = 500, message = "Text should be less than 500 symbols")
    private String text;
    @NotBlank(message = "Author must be entered")
    private String author;
    @NotBlank(message = "Task ID must be entered")
    @Min(value = 1,message = "Task ID can not me less than 1")
    @JsonProperty("task_id")
    private Long taskId;
}
