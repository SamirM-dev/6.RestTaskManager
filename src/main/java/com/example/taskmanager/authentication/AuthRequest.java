package com.example.taskmanager.authentication;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequest {
    @NotBlank(message = "Enter email")
    String email;
    @NotBlank(message = "Enter password")
    String password;

}
