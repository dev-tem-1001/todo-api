package com.example.todo.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TodoApiApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Test
	void getAllTasks_ShouldReturnOk() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks"))
				.andExpect(status().isOk());
	}

	// Тест на корректное создание задачи
	@Test
	void createTask_Success() throws Exception {
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

	void updateTask_CompletedTask() throws Exception {
		String taskJson = "{ \"title\": \"Новая задача\"," +
				"\"description\": \"Тест\"," +
				"\"completed\": false }";


	}

	@Test
	void contextLoads() {
	}

}
