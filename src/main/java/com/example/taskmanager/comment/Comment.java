package com.example.taskmanager.comment;

import com.example.taskmanager.task.Task;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;


@Entity
@Table(name = "comments")
@Getter
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private String author;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "task_id")
    private Task task;

    public Comment() {}

    public Comment(String text, String author, Task task) {
        this.text = text;
        this.author = author;
        this.task = task;
        this.createdAt = LocalDateTime.now();
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
