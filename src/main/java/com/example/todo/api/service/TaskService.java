package com.example.todo.api.service;

import com.example.todo.api.dto.TaskRequestDto;
import com.example.todo.api.dto.TaskResponseDto;
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
    public TaskResponseDto mapToDto(Task task) {
        return TaskResponseDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.isCompleted())
                .build();
    }

    // DTO -> Entity
    public Task mapToEntity(TaskRequestDto taskRequestDto) {
        Task task = new Task();

        task.setTitle(taskRequestDto.getTitle());
        task.setDescription(taskRequestDto.getDescription());
        task.setCompleted(taskRequestDto.isCompleted());

        return task;
    }

    // CREATE
    public TaskResponseDto createTask(TaskRequestDto taskRequestDto) {
        Task task = mapToEntity(taskRequestDto);

        Task savedTask = taskRepository.save(task);

        log.info("Создана новая задача: {}", savedTask);

        return mapToDto(savedTask);
    }

    // GET
    public TaskResponseDto getById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Задача с ID: " + id +" не найдена"));

        log.info("Задача c ID: {} получена: {}", id, task);

        return mapToDto(task);
    }

    // GET
    public List<TaskResponseDto> getAllTasks() {
        List<Task> allTasks = taskRepository.findAll();

        List<TaskResponseDto> listFromTaskToTaskDto = allTasks
                .stream()
                .map(task -> mapToDto(task))
                .toList();

        log.info("Все задачи получены: {}", listFromTaskToTaskDto);

        return listFromTaskToTaskDto;
    }

    // PUT
    public TaskResponseDto updateTask(Long id, TaskRequestDto taskRequestDto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Задача с ID: " + id +" не найдена"));

        if (task.isCompleted()) {
            throw new TaskCompletedException("Нельзя изменить завершенную задачу!");
        }

        task.setTitle(taskRequestDto.getTitle());
        task.setDescription(taskRequestDto.getDescription());
        task.setCompleted(taskRequestDto.isCompleted());

        taskRepository.save(task);

        log.info("Задача обновлена: {}", task);

        return mapToDto(task);
    }

    // DELETE
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Задача ID: {} не найдена", id);
                    throw new TaskNotFoundException("Задача с ID: " + id + " не найдена");
                });

        log.info("Задача удалена: {}", task);

        taskRepository.delete(task);
    }
}
