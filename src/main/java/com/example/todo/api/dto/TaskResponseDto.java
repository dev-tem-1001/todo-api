package com.example.todo.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// для GET (ответ)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDto {

    private Long id;

    private String title;

    private String description;

    private boolean completed = false;
}
