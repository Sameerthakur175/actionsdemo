package com.example.Github;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class Controller {

    // We'll use a simple in-memory list.
    // In a real app, this would be a database.
    private final List<Task> tasks = new ArrayList<>();

    // A simple counter to generate unique IDs
    private final AtomicLong counter = new AtomicLong();

    // Handles GET /tasks
    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return tasks;
    }

    // Handles POST /tasks
    // @RequestBody tells Spring to read the 'Task' from the request's JSON body
    @PostMapping("/tasks")
    public Task createTask(@RequestBody Task task) {
        // Set a new, unique ID from our server-side counter
        task.setId(counter.incrementAndGet());
        tasks.add(task);
        return task;
    }
}
