package springbootApplication.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
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
    private String title;
    private Difficulty difficulty;
    private List<IngredientDto> ingredients;
    private int preparationTime;
    private String instructions;

    public RecipeRequestDto(String title, Difficulty difficulty, int preparationTime) {
        this.title = title;
        this.difficulty = difficulty;
        this.preparationTime = preparationTime;
    }


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
