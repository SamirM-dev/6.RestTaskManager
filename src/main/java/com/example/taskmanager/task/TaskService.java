package com.example.taskmanager.task;

import com.example.taskmanager.enums.TaskPriority;
import com.example.taskmanager.enums.TaskStatus;
import com.example.taskmanager.helper.EntityFinder;
import com.example.taskmanager.helper.TaskMapper;
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
    private final TaskMapper taskMapper;

    public TaskResponse create(TaskCreateRequest request){
        User user = userService.idCheck(request.getUserId());
        Task created=new Task(request.getTitle(), request.getDescription(), TaskPriority.valueOf(request.getPriority()),user);
        user.addTask(created);
        return taskMapper.toResponse(taskRepository.save(created));
    }

    public TaskResponse findById(Long id){
        return taskMapper.toResponse(idCheck(id));
    }

    public TaskResponse update(Long id,TaskUpdateRequest request){
        Task task=idCheck(id);
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(TaskStatus.valueOf(request.getStatus()));
        task.setPriority(TaskPriority.valueOf(request.getPriority()));
        return taskMapper.toResponse(taskRepository.save(task));
    }

    public TaskResponse statusUpdate(Long id,TaskStatusUpdateRequest request){
        Task task=idCheck(id);
        task.setStatus(TaskStatus.valueOf(request.getStatus()));
        return taskMapper.toResponse(taskRepository.save(task));
    }

    public void delete(Long id){
        Task task=idCheck(id);
        task.getUser().removeTask(task);
        taskRepository.delete(task);
    }

   public List<TaskResponse> getTasksWithPaginationAndFilters(String status,String priority,Pageable pageable){
        userService.sortCheck(pageable);
        TaskStatus status1 = status==null?null:TaskStatus.valueOf(status);
        TaskPriority priority1 = priority==null?null:TaskPriority.valueOf(priority);
       Page<Task> tasks = taskRepository.findByFilters(status1, priority1, pageable);
        return tasks.stream().map(taskMapper::toResponse).toList();
   }




    public Task idCheck(Long id){
       return EntityFinder.findOrThrow(taskRepository,id,"Task");
    }
}
