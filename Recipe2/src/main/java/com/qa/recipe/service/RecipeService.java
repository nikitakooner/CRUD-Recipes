package com.qa.recipe.service;

import java.util.List;



import org.springframework.stereotype.Service;

import com.qa.recipe.persistance.domain.Recipe;
import com.qa.recipe.persistance.domain.RecipeRepo;

@Service
public class RecipeService {

	private RecipeRepo recipeRepo;

	public RecipeService(RecipeRepo recipeRepo) {
		super();
		this.recipeRepo = recipeRepo;
	}

	public Recipe createRecipe(Recipe recipe) {
		return this.recipeRepo.save(recipe);
	}

	public List<Recipe> getAllRecipe() {
		return this.recipeRepo.findAll();
	}

	public boolean deleteRecipe(int id) {
		if (this.recipeRepo.existsById(id)) {
			this.recipeRepo.deleteById(id);
			return !this.recipeRepo.existsById(id);
		} else {
			return false;
		}
	}

	public Recipe patchRecipe(int id ,int update) {
		Recipe oldRecipe = this.recipeRepo.findById(id).get();
		oldRecipe.setName(oldRecipe.getName()+ update);
		return this.recipeRepo.save(oldRecipe);
	}

	
}
