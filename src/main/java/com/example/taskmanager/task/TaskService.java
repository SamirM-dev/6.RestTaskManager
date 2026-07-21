package com.example.taskmanager.task;

import com.example.taskmanager.enums.TaskPriority;
import com.example.taskmanager.enums.TaskStatus;
import com.example.taskmanager.user.User;
import com.example.taskmanager.user.UserRepository;
import com.example.taskmanager.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskResponse create(TaskCreateRequest request){
        User user = userService.findUserById(request.getUserId());
        return toResponse(taskRepository.save(new Task(request.getTitle(), request.getDescription(), TaskPriority.valueOf(request.getPriority()),user)));
    }

    public TaskResponse findById(Long id){
        if(id<=0){
            throw new IllegalArgumentException("Id is not valid");
        }
        Task task=taskRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Task with id: "+id+" does not exists"));
        return toResponse(task);
    }

    public TaskResponse update(Long id,TaskUpdateRequest request){
        if(id<=0){
            throw new IllegalArgumentException("Id is not valid");
        }
        Task task=taskRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Task with id: "+id+" does not exists"));
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(TaskStatus.valueOf(request.getStatus()));
        task.setPriority(TaskPriority.valueOf(request.getPriority()));
        return toResponse(taskRepository.save(task));
    }

    public TaskResponse statusUpdate(Long id,TaskStatusUpdateRequest request){
        if(id<=0){
            throw new IllegalArgumentException("Id is not valid");
        }
        Task task=taskRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Task with id: "+id+" does not exists"));
        task.setStatus(TaskStatus.valueOf(request.getStatus()));
        return toResponse(taskRepository.save(task));
    }

    public void delete(Long id){
        if(id<=0){
            throw new IllegalArgumentException("Id is not valid");
        }
        Task task=taskRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Task with id: "+id+" does not exists"));
        taskRepository.delete(task);
    }

    public List<TaskResponse> findByUserIdWithPagination(Long id, Pageable pageable){
        Page<Task> tasks = taskRepository.getByUser_Id(id,pageable);
        List<TaskResponse> result = new ArrayList<>();
        for (Task t : tasks.getContent()){
            result.add(toResponse(t));
        }
        return result;
   }

   public List<TaskResponse> getTasksWithPaginationAndFilters(String status,String priority,Pageable pageable){
        Page<Task> tasks=taskRepository.getByStatusAndPriority(TaskStatus.valueOf(status),TaskPriority.valueOf(priority),pageable);
        List<TaskResponse> result = new ArrayList<>();
        for (Task t:tasks.getContent()){
            result.add(toResponse(t));
        }
        return result;
   }

    private TaskResponse toResponse(Task task){
        TaskResponse response=new TaskResponse();
        response.setId(task.getId());
        response.setTitle(task.getTitle());
        response.setDescription(task.getDescription());
        response.setStatus(task.getStatus());
        response.setPriority(task.getPriority());
        response.setCreatedAt(task.getCreatedAt());
        response.setUpdatedAt(task.getUpdatedAt());
        response.setUserId(task.getUser().getId());
        return response;
    }
}
