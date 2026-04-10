package com.example.todo.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// POST/PUT (запрос)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDto {

    @NotBlank(message = "Заголовок не может быть пустым")
    @Size(min = 1, max = 200, message = "Заголовок должен содержать от 1 до 200 символов")
    private String title;

    @Size(max = 1000)
    private String description;

    private boolean completed = false;
}
