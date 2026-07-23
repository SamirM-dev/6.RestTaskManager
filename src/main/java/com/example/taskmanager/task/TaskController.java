package com.example.taskmanager.task;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1") public class TaskController {

    private final TaskService taskService;

    @PostMapping("/tasks")
    public ResponseEntity<TaskResponse> create(@RequestBody@Valid TaskCreateRequest request){
        TaskResponse response = taskService.create(request);
        return ResponseEntity.created(URI.create("/api/v1/tasks/"+response.getId())).body(response);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskResponse> getById(@PathVariable Long id){
        return ResponseEntity.ok(taskService.findById(id));
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskResponse> update(@PathVariable Long id,@RequestBody @Valid TaskUpdateRequest request){
        return ResponseEntity.ok(taskService.update(id,request));
    }

    @PatchMapping("/tasks/{id}/status")
    public ResponseEntity<TaskResponse> statusUpdate(@PathVariable Long id,@RequestBody @Valid TaskStatusUpdateRequest request){
        return ResponseEntity.ok(taskService.statusUpdate(id,request));
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        taskService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponse>> getAll(@RequestParam(required = false) String status, @RequestParam(required = false) String priority, Pageable pageable){
        return ResponseEntity.ok(taskService.getTasksWithPaginationAndFilters(status,priority,pageable));
    }


}
