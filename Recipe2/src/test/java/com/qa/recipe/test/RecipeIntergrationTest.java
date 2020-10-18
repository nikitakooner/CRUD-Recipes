package com.qa.recipe.test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.recipe.persistance.domain.Recipe;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = { "classpath:recipe-schema.sql",
		"classpath:recipe-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles(profiles = "test")
public class RecipeIntergrationTest {


	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	void testCreate() throws Exception {

		Recipe newRecipe = new Recipe("lemon","lemon","lemon");
		String requestBody = this.mapper.writeValueAsString(newRecipe);
		RequestBuilder request = post("/create/recipe").contentType(MediaType.APPLICATION_JSON).content(requestBody);

		ResultMatcher checkStatus = status().isCreated();

		Recipe savedRecipe = new Recipe("lemon","lemon","lemon");
		savedRecipe.setId(2);

		String resultBody = this.mapper.writeValueAsString(savedRecipe);
		ResultMatcher checkBody = content().json(resultBody);

		this.mockMvc.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}

	@Test
	void testDelete() throws Exception {

		int ID_TO_DELETE = 1;

		RequestBuilder request = delete("/delete/recipe/{id}" + ID_TO_DELETE);

		ResultMatcher checkStatus = status().isOk();

		this.mockMvc.perform(request).andExpect(checkStatus);

	}

	@Test
	void testNotDelete() throws Exception {
		int ID_TO_DELETE = 99;

		RequestBuilder request = delete("/delete/recipe/{id}" + ID_TO_DELETE);

		ResultMatcher checkStatus = status().isBadRequest();

		this.mockMvc.perform(request).andExpect(checkStatus);
	}

	@Test
	void testUpdate() throws Exception {

		String ID_TO_EDIT = "1";
		String requestBodyAdd = this.mapper.writeValueAsString(1);
		RequestBuilder request = patch("/patch/recipe/{id}" + ID_TO_EDIT).contentType(MediaType.APPLICATION_JSON)
				.content(requestBodyAdd);

		ResultMatcher checkStatus = status().isAccepted();

		this.mockMvc.perform(request).andExpect(checkStatus);

		String requestBodyMinus = this.mapper.writeValueAsString(-1);
		request = patch("/patch/recipe/{id}" + ID_TO_EDIT).contentType(MediaType.APPLICATION_JSON)
				.content(requestBodyMinus);

		this.mockMvc.perform(request).andExpect(checkStatus);
	}

	@Test
	void testGetRecipe() throws Exception {
		Recipe recipe = new Recipe("lemon","lemon","cake");
		recipe.setId(1);

		List<Recipe> recipes = new ArrayList<>();
		recipes.add(recipe);
		String responseBody = this.mapper.writeValueAsString(recipe);

		RequestBuilder request = get("/get/allRecipe").contentType(MediaType.APPLICATION_JSON).content(responseBody);

		ResultMatcher checkStatus = status().isOk();

		this.mockMvc.perform(request).andExpect(checkStatus);
		System.out.println(responseBody);
	}

}
