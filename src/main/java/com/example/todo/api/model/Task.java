package com.example.todo.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @NotBlank(message = "Заголовок не может быть пустым")
    @Size(min = 1, max = 200, message = "Заголовок должен содержать от 1 до 200 символов")
    @Column(nullable = false, length = 200)
    private String title;


    @Size(max = 1000)
    private String description;

    @Column(nullable = false)
    private boolean completed = false;

}
