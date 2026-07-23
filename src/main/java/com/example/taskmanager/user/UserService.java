package com.example.taskmanager.user;

import com.example.taskmanager.comment.Comment;
import com.example.taskmanager.exception.ResourceAlreadyExistsException;
import com.example.taskmanager.helper.EntityFinder;
import com.example.taskmanager.task.TaskResponse;
import com.example.taskmanager.task.TaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TaskService taskService;

    public UserResponse create(UserCreateRequest request){
        User findedUser = userRepository.findByEmail(request.getEmail()).orElse(null);
        if(findedUser!=null){
            throw new ResourceAlreadyExistsException("User with this email already exists");
        }
        User created = userRepository.save(new User(request.getName(), request.getEmail(),request.getPassword()));
        return toResponse(created);
    }

    public UserResponse findBYId(Long id){
        return toResponse(idCheck(id));
    }

    public List<TaskResponse> findTasksByUserIdWithPagination(Long id, Pageable pageable){
        idCheck(id);
        return taskService.findByUserIdWithPagination(id,pageable);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }
public UserResponse toResponse(User user){
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setCreatedAt(user.getCreatedAt());
        response.setTasks(user.getTasks().stream().map(taskService::toResponse).toList());
        return response;
    }

    public User idCheck(Long id){
        return EntityFinder.findOrThrow(userRepository,id,"User");
    }



}
