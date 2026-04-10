package com.example.todo.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TodoApiApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void getAllTasks_ShouldReturnOk() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks"))
				.andExpect(status().isOk());
	}

	@Test
	void contextLoads() {
	}

}
