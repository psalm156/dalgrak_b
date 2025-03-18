package springbootApplication.repository;

import springbootApplication.domain.Ingredient; // 올바른 경로로 수정
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
