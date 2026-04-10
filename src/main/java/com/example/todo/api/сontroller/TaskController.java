package com.example.todo.api.сontroller;

import com.example.todo.api.dto.TaskRequestDto;
import com.example.todo.api.dto.TaskResponseDto;
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
    public TaskResponseDto createTask(@Valid @RequestBody TaskRequestDto taskRequestDto) {
        return taskService.createTask(taskRequestDto);
    }

    @GetMapping("/tasks/{id}")
    public TaskResponseDto getTask(@PathVariable Long id) {
        return taskService.getById(id);
    }

    @GetMapping("/tasks")
    public List<TaskResponseDto> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PutMapping("/tasks/{id}")
    public TaskResponseDto updateTask(@PathVariable Long id, @RequestBody TaskRequestDto taskRequestDto) {
        return taskService.updateTask(id, taskRequestDto);
    }

    @DeleteMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Ничего не возвращаем
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
