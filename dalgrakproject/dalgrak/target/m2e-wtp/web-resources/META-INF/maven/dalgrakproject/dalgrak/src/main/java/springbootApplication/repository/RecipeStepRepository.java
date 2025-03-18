package springbootApplication.repository;

import springbootApplication.domain.RecipeStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface RecipeStepRepository extends JpaRepository<RecipeStep, Long> {
   
}
