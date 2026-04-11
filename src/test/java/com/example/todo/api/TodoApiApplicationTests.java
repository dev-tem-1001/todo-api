package com.example.todo.api;

import com.example.todo.api.dto.TaskRequestDto;
import com.example.todo.api.dto.TaskResponseDto;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TodoApiApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	private TestRestTemplate testRestTemplate;

	// Тест на получение всех задач
	@Test
	void getAllTasks() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks"))
				.andExpect(status().isOk());
	}

	// Тест на корректное создание задачи
	@Test
	void createTask() throws Exception {
		String taskJson = "{ \"title\": \"Новая задача\"," +
				"\"description\": \"Тест\"," +
				"\"completed\": false }";

		mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks")
				.contentType(MediaType.APPLICATION_JSON)
				.content(taskJson))

				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.title").value("Новая задача"))
				.andExpect(jsonPath("$.id").exists())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	// Тест на валидацию данных
	@Test
	void createTask_WithInvalidTitle() throws Exception {
		String taskJson = "{ \"title\": \"\"," +
				"\"description\": \"Тест\"," +
				"\"completed\": false }";

		mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks")
				.contentType(MediaType.APPLICATION_JSON)
				.content(taskJson))

				.andExpect(status().isBadRequest());
	}

	// Тест на задачу, которой не существует
	@Test
	void getTaskById_NotFound() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/99999"))
				.andExpect(status().isNotFound());
	}

	// Тест на изменение задачи, которая выполнена
	@Test
	void updateTask_CompletedTask() throws Exception {

		// Создаем задачу
		TaskRequestDto requestDto = TaskRequestDto.builder()
				.title("Новая задача")
				.description("Тест")
				.completed(true)
				.build();

		// Отправляем задачу (POST) с помощью RestTemplate (для извлечения id)
		ResponseEntity<TaskResponseDto> response = testRestTemplate.postForEntity(
				"/api/tasks",
				requestDto,
				TaskResponseDto.class
		);

		// Достаем id
		TaskResponseDto responseDto = response.getBody();
		Long testId = responseDto.getId();

		String updateTaskJson = "{ \"title\": \"Обновленная новая задача\"," +
				"\"description\": \"Обновленный тест\"," +
				"\"completed\": true }";

		mockMvc.perform(MockMvcRequestBuilders.put("/api/tasks/" + testId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(updateTaskJson))

				.andExpect(status().isBadRequest());
	}

	// Тест на удаление задачи
	@Test
	void deleteTask() throws Exception {

		// Создаем задачу
		TaskRequestDto requestDto = TaskRequestDto.builder()
				.title("Новая задача")
				.description("Тест")
				.completed(true)
				.build();

		// Отправляем задачу (POST) с помощью RestTemplate (для извлечения id)
		ResponseEntity<TaskResponseDto> response = testRestTemplate.postForEntity(
				"/api/tasks",
				requestDto,
				TaskResponseDto.class
		);

		// Достаем id
		TaskResponseDto responseDto = response.getBody();
		Long testId = responseDto.getId();

		mockMvc.perform(MockMvcRequestBuilders.delete("/api/tasks/" + testId))
				.andExpect(status().isNoContent());
	}

	@Test
	void contextLoads() {

	}
}
