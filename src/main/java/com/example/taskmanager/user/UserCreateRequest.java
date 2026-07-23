package com.example.taskmanager.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateRequest {
    @NotBlank(message = "Name must be entered")
    private String name;
    @NotBlank(message = "Email must be entered")
    @Email(message = "Not valid email")
    private String email;
    @NotBlank(message = "Password must be entered")
    @Size(min = 8 , message = "Password at least should has 8 symbol")
    private String password;
}
