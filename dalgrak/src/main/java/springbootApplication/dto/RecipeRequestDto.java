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
        Recipe recipe = new Recipe();
        recipe.setTitle(this.title);
        recipe.setDifficulty(this.difficulty);
        recipe.setPreparationTime(this.preparationTime);
        recipe.setInstructions(this.instructions);

        List<RecipeIngredient> recipeIngredients = this.ingredients.stream()
            .map(dto -> new RecipeIngredient(dto.getName(), dto.getQuantity(), recipe))  // Ingredient 대신 String
            .collect(Collectors.toList());

        recipe.setIngredients(recipeIngredients);
        return recipe;
    }

}
