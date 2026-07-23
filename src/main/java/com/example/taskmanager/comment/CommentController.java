package com.example.taskmanager.comment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1") public class CommentController {

    private final CommentService commentService;

    @GetMapping("/tasks/{taskId}/comments")
    public ResponseEntity<List<CommentResponse>> getAllCommentsByTaskId(@PathVariable Long taskId) {
        return ResponseEntity.ok(commentService.getCommentsByTask(taskId));
    }

    @PostMapping("/tasks/{taskId}/comments")
    public ResponseEntity<CommentResponse> create(@PathVariable Long taskId,@RequestBody @Valid CommentCreateRequest request){
        CommentResponse created = commentService.create(taskId,request);
        return ResponseEntity.created(URI.create("/api/v1/tasks/{taskId}/comments/"+created.getId())).body(created);
    }

    @DeleteMapping("/tasks/{taskId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> delete(@PathVariable Long taskId,@PathVariable Long commentId) {
        commentService.deleteByTask(taskId,commentId);
        return ResponseEntity.noContent().build();
    }
}
