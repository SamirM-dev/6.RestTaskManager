package com.example.taskmanager.comment;

import com.example.taskmanager.helper.EntityFinder;
import com.example.taskmanager.task.Task;
import com.example.taskmanager.task.TaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskService taskService;

    public  CommentResponse create(Long taskId,CommentCreateRequest request){
        Task task = taskService.idCheck(taskId);
        Comment newComment= new Comment(request.getText(),request.getAuthor(),task);
        task.addComment(newComment);
        return toResponse(commentRepository.save(newComment));
    }

    public List<CommentResponse> getCommentsByTask(Long taskId){
        Task task = taskService.idCheck(taskId);
        List<CommentResponse> result = new ArrayList<>();
        for (Comment c:task.getComments()){
            result.add(toResponse(c));
        }
        return result;
    }

    public void deleteByTask(Long taskId,Long commentId){
        Task task = taskService.idCheck(taskId);
        Comment comment=idCheck(commentId);
        if(task.getComments().contains(comment)){
            task.getComments().remove(comment);
            commentRepository.delete(comment);
        }
        else {
            throw new EntityNotFoundException("Comment " + commentId + " does not belong to task " + taskId);
        }
    }


    private CommentResponse toResponse(Comment comment){
        CommentResponse response = new CommentResponse();
        response.setId(comment.getId());
        response.setAuthor(comment.getAuthor());
        response.setText(comment.getText());
        response.setCreatedAt(comment.getCreatedAt());
        response.setTaskId(comment.getTask().getId());
        return response;
    }

    public Comment idCheck(Long id){
        return EntityFinder.findOrThrow(commentRepository,id,"Comment");
    }
}
