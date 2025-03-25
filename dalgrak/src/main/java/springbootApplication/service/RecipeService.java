package springbootApplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootApplication.domain.Recipe;
import springbootApplication.dto.RecipeRequestDto;
import springbootApplication.domain.Difficulty;
import springbootApplication.repository.RecipeRepository;
import springbootApplication.service.WebPushService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final WebPushService webPushService; 


    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    @Transactional
    public Recipe saveRecipe(RecipeRequestDto dto) {
    	if (dto.getDifficulty() == null) { 
            throw new RuntimeException("Difficulty value cannot be null");  
        }

        Difficulty difficulty;
        try {
            difficulty = dto.getDifficulty();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid difficulty value");
        }
        
        Recipe recipe = new Recipe();
        recipe.setTitle(dto.getTitle());
        recipe.setDifficulty(difficulty);
        recipe.setPreparationTime(dto.getPreparationTime());

        return recipeRepository.save(recipe); 
    }

    @Transactional
    public Recipe updateRecipe(Long id, Recipe updatedRecipe) {
        return recipeRepository.findById(id)
                .map(existingRecipe -> {
                    existingRecipe.setTitle(updatedRecipe.getTitle());
                    existingRecipe.setInstructions(updatedRecipe.getInstructions());
                    existingRecipe.setPreparationTime(updatedRecipe.getPreparationTime());
                    existingRecipe.setDifficulty(updatedRecipe.getDifficulty());
                    return recipeRepository.save(existingRecipe);
                })
                .orElseThrow(() -> new RuntimeException("Recipe not found"));
    }


    @Transactional
    public void deleteRecipe(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new RuntimeException("Recipe not found");
        }
        recipeRepository.deleteById(id);
    }


    public List<Recipe> findRecipesByKeyword(String keyword) {
        return recipeRepository.findByTitleContaining(keyword);
    }


    public List<Recipe> findByDifficulty(Difficulty difficulty) {
        return recipeRepository.findByDifficulty(difficulty);
    }

    
    public List<Recipe> findRecipesByPreparationTime(int preparationTime) {
        return recipeRepository.findByPreparationTime(preparationTime);
    }
}
