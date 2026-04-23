package com.example.todo.api.controller;

import com.example.todo.api.dto.TaskRequestDto;
import com.example.todo.api.dto.TaskResponseDto;
import com.example.todo.api.exception.TaskNotFoundException;
import com.example.todo.api.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Mockito (вместо реальных сервисов и бд, мы подставляем вручную данные.
    // Быстро, но проверяем "игрушечную" модель
    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;


    // Тест на успешное получение задачи
    @Test
    void getTaskById_Success() throws Exception {
        Long testId = 1L;

        TaskResponseDto mockResponse = TaskResponseDto.builder()
                .id(testId)
                .title("Новая задача")
                .description("Тест")
                .completed(false)
                .build();

        Mockito.when(taskService.getById(testId)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/tasks/" + testId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Новая задача"));
    }

    // Тест на получение несуществующей задачи
    @Test
    void getTaskById_NotFound() throws Exception {
        Long testId = 999L;

        Mockito.when(taskService.getById(testId)).thenThrow(new TaskNotFoundException("Задача не найдена"));

        mockMvc.perform(get("/api/tasks/" + testId))
                .andExpect(status().isNotFound());
    }

    // Тест на создание новой задачи
    @Test
    void createTask_Success() throws Exception {
        TaskRequestDto mockRequest = TaskRequestDto.builder()
                .title("Новая задача")
                .description("Тест")
                .completed(false)
                .build();

        Long testId = 1L;

        TaskResponseDto mockResponse = TaskResponseDto.builder()
                .id(testId)
                .title("Новая задача")
                .description("Тест")
                .completed(false)
                .build();

        Mockito.when(taskService.createTask(any(TaskRequestDto.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockRequest))) // Авто-преобразование в JSON

                .andExpect(status().isCreated()) // Ждем 201
                .andExpect(jsonPath("$.id").value(testId))
                .andExpect(jsonPath("$.title").value("Новая задача"));
    }

}
