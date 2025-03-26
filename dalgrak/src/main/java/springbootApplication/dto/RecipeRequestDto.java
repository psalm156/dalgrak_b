package springbootApplication.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springbootApplication.domain.Difficulty;
import springbootApplication.domain.Ingredient;
import springbootApplication.repository.IngredientRepository;
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


    public Recipe toEntity(List<Ingredient> ingredientList) {
        Recipe recipe = new Recipe();
        recipe.setTitle(this.title);
        recipe.setDifficulty(this.difficulty);
        recipe.setPreparationTime(this.preparationTime);
        recipe.setInstructions(this.instructions);

        List<RecipeIngredient> recipeIngredients = this.ingredients.stream().map(dto -> {
            Ingredient ingredient = ingredientList.stream()
                    .filter(i -> i.getName().equals(dto.getName()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Ingredient not found: " + dto.getName()));

            RecipeIngredient recipeIngredient = new RecipeIngredient();
            recipeIngredient.setIngredient(ingredient);  // Ingredient 객체 설정
            recipeIngredient.setQuantity(dto.getQuantity());
            recipeIngredient.setRecipe(recipe);
            return recipeIngredient;
        }).collect(Collectors.toList());

        recipe.setIngredients(recipeIngredients);
        return recipe;
    }

}
