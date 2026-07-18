package com.example.taskmanager.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TaskPriorityValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TaskPriorityValid {
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}
