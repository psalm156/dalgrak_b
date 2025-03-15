package springbootApplication.service;

import springbootApplication.domain.RecipeStep;
import springbootApplication.repository.RecipeStepRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeStepService {

    private final RecipeStepRepository recipeStepRepository;

    public RecipeStepService(RecipeStepRepository recipeStepRepository) {
        this.recipeStepRepository = recipeStepRepository;
    }

    // 모든 레시피 단계를 조회
    public List<RecipeStep> getAllRecipeSteps() {
        return recipeStepRepository.findAll();
    }

    // ID로 특정 레시피 단계를 조회
    public Optional<RecipeStep> getRecipeStepById(Long id) {
        return recipeStepRepository.findById(id);
    }

    // 새로운 레시피 단계를 생성
    public RecipeStep createRecipeStep(RecipeStep recipeStep) {
        return recipeStepRepository.save(recipeStep);
    }

    // 특정 ID의 레시피 단계를 업데이트
    public RecipeStep updateRecipeStep(Long id, RecipeStep updatedRecipeStep) {
        return recipeStepRepository.findById(id)
                .map(recipeStep -> {
                    recipeStep.setStepNumber(updatedRecipeStep.getStepNumber());
                    recipeStep.setInstruction(updatedRecipeStep.getInstruction());
                    recipeStep.setEstimatedTime(updatedRecipeStep.getEstimatedTime());
                    return recipeStepRepository.save(recipeStep);
                }).orElseThrow(() -> new RuntimeException("Recipe step not found"));
    }

    // 특정 ID의 레시피 단계를 삭제
    public void deleteRecipeStep(Long id) {
        recipeStepRepository.deleteById(id);
    }
}
