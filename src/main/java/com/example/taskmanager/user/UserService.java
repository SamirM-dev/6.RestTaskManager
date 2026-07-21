package com.example.taskmanager.user;

import com.example.taskmanager.exception.ResourceAlreadyExistsException;
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
        if (id<=0){
            throw new IllegalArgumentException("Not Valid id");
        }
        User findedUser = userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User with id: "+id+" does not exist"));
        return toResponse(findedUser);
    }

    public List<TaskResponse> findTasksByUserIdWithPagination(Long id, Pageable pageable){
        if (id<=0){
            throw new IllegalArgumentException("Not Valid id");
        }
        User findedUser = userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User with id: "+id+" does not exist"));
        return taskService.findByUserIdWithPagination(id,pageable);
    }

    public User findUserById(Long id){
        if (id<=0){
            throw new IllegalArgumentException("Not Valid id");
        }
        return userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User with id: "+id+" does not exist"));
    }



    private UserResponse toResponse(User user){
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setCreatedAt(user.getCreatedAt());
        response.setTasks(user.getTasks());
        return response;
    }



}
