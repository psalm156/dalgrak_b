package springbootApplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootApplication.domain.Ingredient;
import springbootApplication.domain.Recipe;
import springbootApplication.domain.RecipeIngredient;
import springbootApplication.repository.IngredientRepository;
import springbootApplication.repository.RecipeRepository;

@Service
public class RecipeIngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    public RecipeIngredient createRecipeIngredient(Recipe recipe, String ingredientName, String quantity) {
        // ingredientName을 통해 Ingredient 객체를 찾음
        Ingredient ingredient = ingredientRepository.findByName(ingredientName)
                .orElseThrow(() -> new RuntimeException("Ingredient not found: " + ingredientName));

        // RecipeIngredient 객체 생성
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setRecipe(recipe); // Recipe 설정
        recipeIngredient.setIngredient(ingredient); // Ingredient 객체를 설정
        recipeIngredient.setQuantity(quantity); // Quantity 설정

        return recipeIngredient;
    }
}

