package com.example.taskmanager.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TaskStatus {
    @JsonProperty("New") NEW,
    @JsonProperty("In progress") IN_PROGRESS,
    @JsonProperty("Done") DONE
}
