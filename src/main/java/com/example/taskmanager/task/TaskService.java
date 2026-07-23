package com.example.taskmanager.task;

import com.example.taskmanager.enums.TaskPriority;
import com.example.taskmanager.enums.TaskStatus;
import com.example.taskmanager.helper.EntityFinder;
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
        User user = userService.idCheck(request.getUserId());
        Task created=new Task(request.getTitle(), request.getDescription(), TaskPriority.valueOf(request.getPriority()),user);
        user.addTask(created);
        return toResponse(taskRepository.save(created));
    }

    public TaskResponse findById(Long id){
        return toResponse(idCheck(id));
    }

    public TaskResponse update(Long id,TaskUpdateRequest request){
        Task task=idCheck(id);
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(TaskStatus.valueOf(request.getStatus()));
        task.setPriority(TaskPriority.valueOf(request.getPriority()));
        return toResponse(taskRepository.save(task));
    }

    public TaskResponse statusUpdate(Long id,TaskStatusUpdateRequest request){
        Task task=idCheck(id);
        task.setStatus(TaskStatus.valueOf(request.getStatus()));
        return toResponse(taskRepository.save(task));
    }

    public void delete(Long id){
        Task task=idCheck(id);
        task.getUser().removeTask(task);
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


    public TaskResponse toResponse(Task task){
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

    public Task idCheck(Long id){
       return EntityFinder.findOrThrow(taskRepository,id,"Task");
    }
}
