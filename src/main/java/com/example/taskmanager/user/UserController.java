package com.example.taskmanager.user;

import com.example.taskmanager.task.TaskResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserResponse> create(@RequestBody@Valid UserCreateRequest request){
        UserResponse created = userService.create(request);
        return ResponseEntity.created(URI.create("/api/v1/users/"+created.getId())).body(created);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id){
        UserResponse finded = userService.findBYId(id);
        return ResponseEntity.ok(finded);
    }

    @GetMapping("/users/{id}/tasks")
    public ResponseEntity<List<TaskResponse>> getAll(@PathVariable Long id, @ParameterObject Pageable pageable){
        return ResponseEntity.ok(userService.findTasksByUserIdWithPagination(id,pageable));
    }
}
