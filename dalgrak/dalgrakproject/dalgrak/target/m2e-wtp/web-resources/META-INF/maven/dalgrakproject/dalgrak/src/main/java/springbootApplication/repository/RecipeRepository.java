package springbootApplication.repository;

import springbootApplication.domain.Recipe;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import springbootApplication.domain.Difficulty;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

	List<Recipe> findByTitleContaining(String keyword);

    List<Recipe> findByCategoryAndDifficultyAndPreparationTime(String category, Difficulty difficulty, int preparationTime);

    List<Recipe> findByCategoryAndDifficulty(String category, Difficulty difficulty);

    List<Recipe> findByCategoryAndPreparationTime(String category, int preparationTime);

    List<Recipe> findByDifficultyAndPreparationTime(Difficulty difficulty, int preparationTime);

    List<Recipe> findByCategory(String category);

    List<Recipe> findByDifficulty(Difficulty difficulty);

    List<Recipe> findByPreparationTime(int preparationTime);
}
