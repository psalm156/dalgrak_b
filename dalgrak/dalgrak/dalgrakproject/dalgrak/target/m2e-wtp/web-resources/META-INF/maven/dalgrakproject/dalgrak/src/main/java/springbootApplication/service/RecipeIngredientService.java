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
 
        Ingredient ingredient = ingredientRepository.findByName(ingredientName)
                .orElseThrow(() -> new RuntimeException("Ingredient not found: " + ingredientName));

        
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setRecipe(recipe);      
        recipeIngredient.setIngredient(ingredientName);
        recipeIngredient.setQuantity(quantity);

        return recipeIngredient;
    }
}