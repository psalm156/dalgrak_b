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

@RestController
@RequestMapping("/api/recipes")
@Tag(name = "Recipes", description = "CRUD & Search operations for recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @Operation(summary = "Get all recipes", description = "Retrieve a list of all recipes")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a recipe by ID", description = "Retrieve a recipe by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Recipe found"),
        @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        return recipeService.getRecipeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
    @Operation(summary = "Filter recipes", description = "Filter recipes by category and difficulty")
    public ResponseEntity<List<Recipe>> filterRecipes(
            @RequestParam String category, 
            @RequestParam Difficulty difficulty) {
        List<Recipe> recipes = recipeService.findByDifficulty(difficulty);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/cooking-time")
    @Operation(summary = "Find recipes by cooking time", description = "Retrieve recipes that match the given cooking time")
    public ResponseEntity<List<Recipe>> findRecipesByCookingTime(@RequestParam int cookingTime) {
        List<Recipe> recipes = recipeService.findRecipesByPreparationTime(cookingTime);
        return ResponseEntity.ok(recipes);
    }
}

