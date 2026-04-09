package com.example.todo.api.сontroller;

import com.example.todo.api.dto.TaskDto;
import com.example.todo.api.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/tasks")
    public TaskDto createTask(@Valid @RequestBody TaskDto taskDto) {
        return taskService.createTask(taskDto);
    }

    @GetMapping("/tasks/{id}")
    public TaskDto getTask(@PathVariable Long id) {
        return taskService.getById(id);
    }

    @GetMapping("/tasks")
    public List<TaskDto> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PutMapping("/tasks/{id}")
    public TaskDto updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        taskDto.setId(id);

        return taskService.updateTask(taskDto);
    }

    @DeleteMapping("/tasks/{id}")
    // @ResponseStatus(HttpStatus.NO_CONTENT) // Ничего не возвращаем
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
