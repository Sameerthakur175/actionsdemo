package com.example.Github;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc // This sets up a mock server environment for testing our controller
class GithubApplicationTests {

	@Autowired
	private MockMvc mvc; // This object lets us perform fake HTTP requests

	@Test
	public void testGetTasks_ReturnsEmptyListInitially() throws Exception {
		// Perform a GET request to /tasks
		mvc.perform(get("/tasks"))
				.andExpect(status().isOk()) // Expect HTTP 200 OK
				.andExpect(jsonPath("$", hasSize(0))); // Expect the JSON response body to be an empty array
	}

	@Test
	public void testPostTask_CreatesNewTask() throws Exception {
		// 1. First, create a new task
		String taskJson = "{\"description\":\"My first task\", \"completed\":false}";

		mvc.perform(post("/tasks")
						.contentType(MediaType.APPLICATION_JSON) // Set the content-type header
						.content(taskJson)) // Set the request body
				.andExpect(status().isOk()) // Expect HTTP 200 OK
				.andExpect(jsonPath("$.id", is(1))) // Expect the response to have id: 1
				.andExpect(jsonPath("$.description", is("My first task")));

		// 2. Now, check if the GET endpoint returns the new task
		mvc.perform(get("/tasks"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1))) // Expect the array to have 1 item
				.andExpect(jsonPath("$[0].description", is("My first task")));
	}
}