package com.example.taskmanager.user;

import com.example.taskmanager.task.Task;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.resilience.annotation.EnableResilientMethods;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH},orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    public User(){}

    public User(String name, String email,String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addTask(Task task){
        this.tasks.add(task);
        task.setUser(this);
    }
    public void removeTask(Task task){
        this.tasks.remove(task);
        task.setUser(null);
    }
}
