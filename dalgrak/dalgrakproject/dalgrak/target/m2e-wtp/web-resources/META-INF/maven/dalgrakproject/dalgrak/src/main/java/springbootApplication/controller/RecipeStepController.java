package springbootApplication.controller;

import springbootApplication.domain.RecipeStep;
import springbootApplication.service.RecipeStepService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipe-steps")
@Tag(name = "Recipe Steps", description = "CRUD operations for recipe steps")
public class RecipeStepController {

    private final RecipeStepService recipeStepService;

    public RecipeStepController(RecipeStepService recipeStepService) {
        this.recipeStepService = recipeStepService;
    }

    @GetMapping
    @Operation(summary = "Get all recipe steps", description = "Retrieve a list of all recipe steps")
    public ResponseEntity<List<RecipeStep>> getAllRecipeSteps() {
        List<RecipeStep> steps = recipeStepService.getAllRecipeSteps();
        if (steps.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(steps);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a recipe step by ID", description = "Retrieve a recipe step by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Recipe step found"),
            @ApiResponse(responseCode = "404", description = "Recipe step not found")
    })
    public ResponseEntity<RecipeStep> getRecipeStepById(@PathVariable Long id) {
        return recipeStepService.getRecipeStepById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new recipe step", description = "Add a new recipe step")
    public ResponseEntity<RecipeStep> createRecipeStep(@RequestBody RecipeStep recipeStep) {
        RecipeStep createdStep = recipeStepService.createRecipeStep(recipeStep);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStep);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing recipe step", description = "Update an existing recipe step")
    public ResponseEntity<RecipeStep> updateRecipeStep(@PathVariable Long id, @RequestBody RecipeStep updatedRecipeStep) {
        try {
            RecipeStep updatedStep = recipeStepService.updateRecipeStep(id, updatedRecipeStep);
            return ResponseEntity.ok(updatedStep);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a recipe step by ID", description = "Delete a recipe step by its ID")
    public ResponseEntity<Void> deleteRecipeStep(@PathVariable Long id) {
        recipeStepService.deleteRecipeStep(id);
        return ResponseEntity.noContent().build();
    }
}
