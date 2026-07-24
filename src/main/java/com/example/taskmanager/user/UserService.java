package com.example.taskmanager.user;

import com.example.taskmanager.exception.ResourceAlreadyExistsException;
import com.example.taskmanager.helper.EntityFinder;
import com.example.taskmanager.helper.TaskMapper;
import com.example.taskmanager.task.Task;
import com.example.taskmanager.task.TaskRepository;
import com.example.taskmanager.task.TaskResponse;
import com.example.taskmanager.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final static Set<String> ALLOWED_SORT_FIELDS = Set.of("id","title","description","status","priority","createdAt","updatedAt","user.id");
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

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
        sortCheck(pageable);
        Page<Task> tasks = taskRepository.getByUser_Id(id,pageable);
        return tasks.getContent().stream().map(taskMapper::toResponse).toList();
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }
public UserResponse toResponse(User user){
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setCreatedAt(user.getCreatedAt());
        response.setTasks(user.getTasks().stream().map(taskMapper::toResponse).toList());
        return response;
    }

    public User idCheck(Long id){
        return EntityFinder.findOrThrow(userRepository,id,"User");
    }

    public void sortCheck(Pageable pageable){
        for(Sort.Order order : pageable.getSort()){
            if(!ALLOWED_SORT_FIELDS.contains(order.getProperty())){
                throw new IllegalArgumentException("Invalid sort field");
            }
        }
    }



}
