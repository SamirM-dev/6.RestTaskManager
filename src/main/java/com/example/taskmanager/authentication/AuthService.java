package com.example.taskmanager.authentication;

import com.example.taskmanager.exception.InvalidCredentialsException;
import com.example.taskmanager.user.User;
import com.example.taskmanager.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    public ResponseCookie login(String email, String password){
        User user = userService.findByEmail(email);
        if(user == null){
            throw new EntityNotFoundException("User with email " + email + " not found");
        }
        String userPassword = user.getPassword();
        if(!userPassword.equals(password)){
            throw new InvalidCredentialsException("Wrong Password");
        }
        String token = UUID.randomUUID().toString();
        return ResponseCookie.from("access_token",token).sameSite("Strict").maxAge(3600).path("/api/v1").httpOnly(true).secure(true).build();

    }

    public ResponseCookie logout(){
       return ResponseCookie.from("access_token","").maxAge(0).path("/api/v1").build();
    }
}
