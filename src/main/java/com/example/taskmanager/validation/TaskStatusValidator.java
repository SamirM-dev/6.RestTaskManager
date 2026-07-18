package com.example.taskmanager.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class TaskStatusValidator implements ConstraintValidator<TaskStatusValid,String> {

    private final List<String> ALLOWED_STATUSES=new ArrayList<>(List.of("NEW","IN_PROGRESS","DONE"));

    @Override
    public void initialize(TaskStatusValid constraintAnnotation) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return ALLOWED_STATUSES.contains(value.toUpperCase());
    }
}
