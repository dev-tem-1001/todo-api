package com.example.todo.api.service;

import com.example.todo.api.dto.TaskDto;
import com.example.todo.api.exception.TaskCompletedException;
import com.example.todo.api.exception.TaskNotFoundException;
import com.example.todo.api.model.Task;
import com.example.todo.api.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    // Entity -> DTO
    public TaskDto mapToDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.isCompleted())
                .build();
    }

    // DTO -> Entity
    public Task mapToEntity(TaskDto taskDto) {
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setCompleted(taskDto.isCompleted());

        return task;
    }

    // CREATE
    public TaskDto createTask(TaskDto taskDto) {
        Task task = mapToEntity(taskDto);

        Task savedTask = taskRepository.save(task);

        log.info("Создана новая задача: {}", savedTask);

        return mapToDto(savedTask);
    }

    // GET
    public TaskDto getById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Задача с ID: " + id +" не найдена"));

        log.info("Задача c ID: {} получена: {}", id, task);

        return mapToDto(task);
    }

    // GET
    public List<TaskDto> getAllTasks() {
        List<Task> allTasks = taskRepository.findAll();

        List<TaskDto> listFromTaskToTaskDto = allTasks
                .stream()
                .map(task -> mapToDto(task))
                .toList();

        log.info("Все задачи получены");

        return listFromTaskToTaskDto;
    }

    // PUT
    public TaskDto updateTask(TaskDto taskDto) {
        Task task = taskRepository.findById(taskDto.getId())
                .orElseThrow(() -> new TaskNotFoundException("Задача с ID: " + taskDto.getId() +" не найдена"));

        if (task.isCompleted()) {
            throw new TaskCompletedException("Нельзя изменить завершенную задачу!");
        }

        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setCompleted(taskDto.isCompleted());

        taskRepository.save(task);

        log.info("Задача обновлена: {}", task);

        return mapToDto(task);
    }

    // DELETE
    public void deleteTask(Long id) {
        Optional<Task> task = taskRepository.findById(id);

        if (task.isEmpty()) {
            log.warn("Задача ID: {} не найдена", id);
            throw new TaskNotFoundException("Задача с ID: " + id +" не найдена");
        }

        log.info("Задача удалена: {}", task);

        taskRepository.delete(task.get());
    }
}
