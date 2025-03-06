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

<<<<<<< HEAD
=======
    // 모든 레시피 단계를 조회
>>>>>>> b498c2463a65cfc0380b23f18ed45e10f2208fb2
    public List<RecipeStep> getAllRecipeSteps() {
        return recipeStepRepository.findAll();
    }

<<<<<<< HEAD
=======
    // ID로 특정 레시피 단계를 조회
>>>>>>> b498c2463a65cfc0380b23f18ed45e10f2208fb2
    public Optional<RecipeStep> getRecipeStepById(Long id) {
        return recipeStepRepository.findById(id);
    }

<<<<<<< HEAD
=======
    // 새로운 레시피 단계를 생성
>>>>>>> b498c2463a65cfc0380b23f18ed45e10f2208fb2
    public RecipeStep createRecipeStep(RecipeStep recipeStep) {
        return recipeStepRepository.save(recipeStep);
    }

<<<<<<< HEAD
=======
    // 특정 ID의 레시피 단계를 업데이트
>>>>>>> b498c2463a65cfc0380b23f18ed45e10f2208fb2
    public RecipeStep updateRecipeStep(Long id, RecipeStep updatedRecipeStep) {
        return recipeStepRepository.findById(id)
                .map(recipeStep -> {
                    recipeStep.setStepNumber(updatedRecipeStep.getStepNumber());
                    recipeStep.setInstruction(updatedRecipeStep.getInstruction());
                    recipeStep.setEstimatedTime(updatedRecipeStep.getEstimatedTime());
                    return recipeStepRepository.save(recipeStep);
                }).orElseThrow(() -> new RuntimeException("Recipe step not found"));
    }

<<<<<<< HEAD
=======
    // 특정 ID의 레시피 단계를 삭제
>>>>>>> b498c2463a65cfc0380b23f18ed45e10f2208fb2
    public void deleteRecipeStep(Long id) {
        recipeStepRepository.deleteById(id);
    }
}
