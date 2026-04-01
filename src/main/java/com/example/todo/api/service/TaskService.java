package com.example.todo.api.service;

import com.example.todo.api.dto.TaskDto;
import com.example.todo.api.exception.TaskCompletedException;
import com.example.todo.api.model.Task;
import com.example.todo.api.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    // Task -> DTO
    public TaskDto createDto(Task task) {
        // если я получил данные task, зачем второй раз лезть в бд
        // Task task = taskRepository.findById(taskToDto.getId()).orElseThrow();
        return TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.isCompleted())
                .build();
    }

    // DTO -> Task
    public TaskDto createTask(TaskDto taskDto) {
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setCompleted(taskDto.isCompleted());

        Task savedTask = taskRepository.save(task);

        return createDto(savedTask);
    }

    // GET
    public TaskDto getById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Задача не найдена"));

        return createDto(task);
    }

    public List<TaskDto> getAllTasks() {
        List<Task> allTasks = taskRepository.findAll();

        List<TaskDto> listFromTaskToTaskDto = allTasks.stream().map(task -> createDto(task)).toList();

        return listFromTaskToTaskDto;
    }

    public TaskDto updateTask(TaskDto taskDto) {
        Task task = taskRepository.findById(taskDto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Задача не найдена"));

        if (task.isCompleted() == true) {
            throw new TaskCompletedException("Нельзя изменить завершенную задачу!");
        }

        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setCompleted(taskDto.isCompleted());

        taskRepository.save(task);

        return createDto(task);
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Задача не найдена");
        }

        taskRepository.deleteById(id);
    }
}
