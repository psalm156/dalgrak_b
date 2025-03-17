package springbootApplication.dto;

import java.util.List;
import java.util.stream.Collectors;

<<<<<<< HEAD
import lombok.AllArgsConstructor;
=======
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
>>>>>>> 7c7b34bd84b35458d4e52b02a4d76aab084129a6
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springbootApplication.domain.Difficulty;
import springbootApplication.domain.Recipe;
import springbootApplication.domain.RecipeIngredient;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeRequestDto {
	
	@NotBlank(message = "Title cannot be blank")
    @Size(max = 100, message = "Title must be at most 100 characters")
    private String title;
	
	@NotNull(message = "Difficulty cannot be null")
    private Difficulty difficulty;
	
	@NotEmpty(message = "Ingredients list cannot be empty")
    private List<@Valid IngredientDto> ingredients;
	
	@Positive(message = "Preparation time must be a positive number")
    private int preparationTime;
	
	@NotBlank(message = "Instructions cannot be blank")
    @Size(min = 30, message = "Instructions must be at least 30 characters long")
    private String instructions;

<<<<<<< HEAD
    public RecipeRequestDto(String title, Difficulty difficulty, int preparationTime) {
        this.title = title;
        this.difficulty = difficulty;
        this.preparationTime = preparationTime;
    }


=======
	 private List<Integer> timerDurations;
 
>>>>>>> 7c7b34bd84b35458d4e52b02a4d76aab084129a6
    public Recipe toEntity() {
    	Recipe recipe= new Recipe();
    	recipe.setTitle(this.title);
    	recipe.setDifficulty(this.difficulty);
    	recipe.setPreparationTime(this.preparationTime);
    	recipe.setInstructions(this.instructions);
    	
    	List<RecipeIngredient> ingredientList=this.ingredients.stream().map(dto -> {
    		RecipeIngredient ingredient = new RecipeIngredient();
    		ingredient.setIngredient(dto.getName());
    		ingredient.setQuantity(dto.getQuantity());
    		ingredient.setRecipe(recipe);
    		return ingredient;
    	}).collect(Collectors.toList());
    	
    	recipe.setIngredients(ingredientList);
		return recipe;
	
    }
}
