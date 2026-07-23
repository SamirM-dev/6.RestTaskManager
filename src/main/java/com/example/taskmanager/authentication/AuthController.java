package com.example.taskmanager.authentication;

import com.example.taskmanager.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody AuthRequest request){
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,authService.login(request.email, request.getPassword()).toString()).build();
    }


    @PostMapping("/logout")
    public ResponseEntity<Void> logout(){
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,authService.logout().toString()).build();
    }

}
