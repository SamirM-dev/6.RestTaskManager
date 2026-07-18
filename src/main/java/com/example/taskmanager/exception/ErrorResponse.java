package com.example.taskmanager.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ErrorResponse {
    private int statusCode;
    private String error;
    private String message;
    private String path;
    @JsonFormat(pattern = "yy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    private List<FieldError> fieldErrors;

    public ErrorResponse(int statusCode, String error, String message, String path) {
        this.statusCode = statusCode;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    @Getter
    public static class FieldError{
        private String field;
        private String message;
        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }

    public void setFieldErrors(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
