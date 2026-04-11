package com.example.todo.api.сontroller;

import com.example.todo.api.dto.TaskRequestDto;
import com.example.todo.api.dto.TaskResponseDto;
import com.example.todo.api.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/tasks")
    public ResponseEntity<TaskResponseDto> createTask(@Valid @RequestBody TaskRequestDto taskRequestDto) {
        return new ResponseEntity<>(taskService.createTask(taskRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("/tasks/{id}")
    public TaskResponseDto getTask(@PathVariable Long id) {
        return taskService.getById(id);
    }

    @GetMapping("/tasks")
    public Page<TaskResponseDto> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return taskService.getAllTasks(page, size);
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
