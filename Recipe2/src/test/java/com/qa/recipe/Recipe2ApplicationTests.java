package com.qa.recipe;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;

import com.qa.recipe.persistance.domain.Recipe;
import nl.jqno.equalsverifier.EqualsVerifier;

@SpringBootTest
@ActiveProfiles(profiles = "test")
class Recipe2ApplicationTests {

	@Test
	void contextLoads() {

	}

	@SpringBootTest
	@ActiveProfiles(profiles = "test")
	class DnDBackendApplicationTests {

		@Test
		void testEqualsVerifier() {
			EqualsVerifier.forClass(Recipe.class).usingGetClass().verify();
		}
}
}
