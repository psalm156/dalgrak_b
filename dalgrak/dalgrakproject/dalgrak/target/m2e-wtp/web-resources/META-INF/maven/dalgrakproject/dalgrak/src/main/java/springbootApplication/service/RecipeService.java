package springbootApplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootApplication.domain.Recipe;
import springbootApplication.domain.Timer;
import springbootApplication.dto.RecipeRequestDto;
import springbootApplication.domain.Difficulty;
import springbootApplication.repository.RecipeRepository;
import springbootApplication.repository.TimerRepository;
import springbootApplication.repository.SubscriptionRepository;
import springbootApplication.domain.Subscription;
import springbootApplication.service.WebPushService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final TimerRepository timerRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final WebPushService webPushService;  // WebPushService 주입

    // 전체 레시피 조회
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    // ID 기준 특정 레시피 조회
    public Optional<Recipe> getRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    // 키워드로 레시피 검색
    public List<Recipe> findRecipesByKeyword(String keyword) {
        return recipeRepository.findByTitleContaining(keyword);
    }

    // 레시피 생성
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

        // 레시피 저장 (타이머와 함께 저장 가능)
        recipeRepository.save(recipe);

        if (dto.getTimerDurations() != null && !dto.getTimerDurations().isEmpty()) {
            for (Integer duration : dto.getTimerDurations()) {
                Timer timer = new Timer(duration);
                recipe.addTimer(timer);
            }
        }

        return recipe;
    }

    // 레시피 수정 (Recipe 객체 사용)
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

    // 레시피 삭제
    @Transactional
    public void deleteRecipe(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new RuntimeException("Recipe not found");
        }
        recipeRepository.deleteById(id);
    }

    // 준비 시간별 레시피 검색
    public List<Recipe> findRecipesByPreparationTime(int preparationTime) {
        return recipeRepository.findByPreparationTime(preparationTime);
    }

    // 필터 적용 검색 (카테고리, 난이도, 준비 시간)
    public List<Recipe> filterRecipes(Optional<String> category, Optional<Difficulty> difficulty, Optional<Integer> preparationTime) {
        String categoryValue = category.orElse(null);
        Difficulty difficultyValue = difficulty.orElse(null);
        Integer prepTimeValue = preparationTime.orElse(null);

        if (categoryValue != null && difficultyValue != null && prepTimeValue != null) {
            return recipeRepository.findByCategoryAndDifficultyAndPreparationTime(categoryValue, difficultyValue, prepTimeValue);
        } else if (categoryValue != null && difficultyValue != null) {
            return recipeRepository.findByCategoryAndDifficulty(categoryValue, difficultyValue);
        } else if (categoryValue != null && prepTimeValue != null) {
            return recipeRepository.findByCategoryAndPreparationTime(categoryValue, prepTimeValue);
        } else if (difficultyValue != null && prepTimeValue != null) {
            return recipeRepository.findByDifficultyAndPreparationTime(difficultyValue, prepTimeValue);
        } else if (categoryValue != null) {
            return recipeRepository.findByCategory(categoryValue);
        } else if (difficultyValue != null) {
            return recipeRepository.findByDifficulty(difficultyValue);
        } else if (prepTimeValue != null) {
            return recipeRepository.findByPreparationTime(prepTimeValue);
        } else {
            return recipeRepository.findAll();
        }
    }
}
