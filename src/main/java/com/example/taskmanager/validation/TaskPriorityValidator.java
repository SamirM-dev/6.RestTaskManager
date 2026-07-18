package com.example.taskmanager.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;

public class TaskPriorityValidator implements ConstraintValidator<TaskPriorityValid,String> {

    private final List<String> ALLOWED_PRIORITIES=new ArrayList<>(List.of("LOW","MEDIUM","HIGH"));

    @Override
    public void initialize(TaskPriorityValid constraintAnnotation) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return ALLOWED_PRIORITIES.contains(value.toUpperCase());
    }
}
