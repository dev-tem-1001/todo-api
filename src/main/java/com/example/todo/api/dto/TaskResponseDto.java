package com.example.todo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    private LocalDateTime createdAt;

}
