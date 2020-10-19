package com.qa.recipe.controller;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.qa.recipe.persistance.domain.Recipe;
import com.qa.recipe.service.RecipeService;

@RestController
@CrossOrigin
public class RecipeController {
	
private RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}

	@GetMapping("/get/allRecipe")
	public ResponseEntity<List<Recipe>> getAllCombat() {
		return ResponseEntity.ok(this.recipeService.getAllRecipe());
	}

	@PostMapping("/create/Recipe")
	public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
		return new ResponseEntity<Recipe>(this.recipeService.createRecipe(recipe), HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/recipe/{id}")
	public ResponseEntity<Object> deleteRecipe(@PathVariable int id) {
		if (this.recipeService.deleteRecipe(id)) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PatchMapping("/patch/recipe/{id}")
	public ResponseEntity<Recipe> patchRecipe(@PathVariable int id, @RequestBody int update) {
		return new ResponseEntity<Recipe>(this.recipeService.patchRecipe(id, update), HttpStatus.ACCEPTED);
	}

}
