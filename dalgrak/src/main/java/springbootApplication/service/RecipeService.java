package springbootApplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootApplication.domain.Recipe;
import springbootApplication.dto.RecipeRequestDto;
import springbootApplication.domain.Difficulty;
import springbootApplication.repository.RecipeRepository;
import springbootApplication.repository.SubscriptionRepository;
import springbootApplication.domain.Subscription;
import springbootApplication.service.WebPushService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final WebPushService webPushService;  // WebPushService 주입


<<<<<<< HEAD
=======
    // 전체 레시피 조회
>>>>>>> b498c2463a65cfc0380b23f18ed45e10f2208fb2
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

<<<<<<< HEAD
    // ID기준 특정 레시피 조회
    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

=======
    public List<Recipe> findRecipesByKeyword(String keyword) {
        return recipeRepository.findByTitleContaining(keyword);
    }

    // 레시피 생성 (Recipe 객체 사용)
>>>>>>> b498c2463a65cfc0380b23f18ed45e10f2208fb2
    @Transactional
    public Recipe saveRecipe(RecipeRequestDto dto) {
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

        return recipeRepository.save(recipe); // save() 호출
    }

<<<<<<< HEAD
=======
    // 레시피 수정 (Recipe 객체 사용)
>>>>>>> b498c2463a65cfc0380b23f18ed45e10f2208fb2
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

<<<<<<< HEAD

=======
    // 레시피 삭제
>>>>>>> b498c2463a65cfc0380b23f18ed45e10f2208fb2
    @Transactional
    public void deleteRecipe(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new RuntimeException("Recipe not found");
        }
        recipeRepository.deleteById(id);
    }

<<<<<<< HEAD

    public List<Recipe> findRecipesByKeyword(String keyword) {
        return recipeRepository.findByTitleContaining(keyword);
    }


    public List<Recipe> findByDifficulty(Difficulty difficulty) {
        return recipeRepository.findByDifficulty(difficulty);
    }

    //준비 시간별 레시피 검색 
    public List<Recipe> findRecipesByPreparationTime(int preparationTime) {
        return recipeRepository.findByPreparationTime(preparationTime);
=======
    public List<Recipe> filterRecipes(Optional<String> category, Optional<Difficulty> difficulty, Optional<Integer> preparationTime) {
        if (category.isPresent() && difficulty.isPresent() && preparationTime.isPresent()) {
            // 모든 필터가 있을 때
            return recipeRepository.findByCategoryAndDifficultyAndPreparationTime(
                    category.get(), difficulty.get(), preparationTime.get());
        } else if (category.isPresent() && difficulty.isPresent()) {
            // category와 difficulty만 있을 때
            return recipeRepository.findByCategoryAndDifficulty(category.get(), difficulty.get());
        } else if (category.isPresent() && preparationTime.isPresent()) {
            // category와 preparationTime만 있을 때
            return recipeRepository.findByCategoryAndPreparationTime(category.get(), preparationTime.get());
        } else if (difficulty.isPresent() && preparationTime.isPresent()) {
            // difficulty와 preparationTime만 있을 때
            return recipeRepository.findByDifficultyAndPreparationTime(difficulty.get(), preparationTime.get());
        } else if (category.isPresent()) {
            // category만 있을 때
            return recipeRepository.findByCategory(category.get());
        } else if (difficulty.isPresent()) {
            // difficulty만 있을 때
            return recipeRepository.findByDifficulty(difficulty.get());
        } else if (preparationTime.isPresent()) {
            // preparationTime만 있을 때
            return recipeRepository.findByPreparationTime(preparationTime.get());
        } else {
            // 모든 필터가 없을 때 (모든 레시피 반환)
            return recipeRepository.findAll();
        }
    
>>>>>>> b498c2463a65cfc0380b23f18ed45e10f2208fb2
    }
}
