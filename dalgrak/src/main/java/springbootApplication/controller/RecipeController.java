package springbootApplication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import springbootApplication.domain.Difficulty;
import springbootApplication.domain.Recipe;
import springbootApplication.dto.RecipeRequestDto;
import springbootApplication.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipes")
@Tag(name = "Recipes", description = "CRUD & Search operations for recipes")
public class RecipeController {

    private final RecipeService recipeService;
	private int preparationTime;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @Operation(summary = "Get all recipes", description = "Retrieve a list of all recipes")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }


    @PostMapping
    @Operation(summary = "Create a new recipe", description = "Add a new recipe")
    public ResponseEntity<Recipe> createRecipe(@RequestBody RecipeRequestDto dto) {
        Recipe savedRecipe = recipeService.saveRecipe(dto);
        return ResponseEntity.ok(savedRecipe);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing recipe", description = "Update an existing recipe")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody RecipeRequestDto dto) {
        try {
        	Recipe recipe = dto.toEntity();
            Recipe updatedRecipe = recipeService.updateRecipe(id, recipe);
            return ResponseEntity.ok(updatedRecipe);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a recipe by ID", description = "Delete a recipe by its ID")
    public ResponseEntity<String> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.ok("Recipe deleted successfully");
    }

    @GetMapping("/search")
    @Operation(summary = "Search recipes by keyword", description = "Search for recipes containing the given keyword")
    public ResponseEntity<List<Recipe>> searchRecipes(@RequestParam String keyword) {
        List<Recipe> recipes = recipeService.findRecipesByKeyword(keyword);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/filter")
    @Operation(summary = "Filter recipes", description = "Filter recipes by category, difficulty, and cooking time")
    public ResponseEntity<List<Recipe>> filterRecipes(
            @RequestParam Optional<String> category,
            @RequestParam Optional<Difficulty> difficulty,
            @RequestParam Optional<Integer> preparationTime) {
        List<Recipe> recipes = recipeService.filterRecipes(category, difficulty, preparationTime);
        return ResponseEntity.ok(recipes);
    }

}

