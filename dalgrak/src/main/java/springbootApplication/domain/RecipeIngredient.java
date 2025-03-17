package springbootApplication.domain;

import springbootApplication.repository.IngredientRepository;
import jakarta.persistence.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;


@Getter
@Setter
@Entity
@Table(name = "recipe_ingredients")
public class RecipeIngredient {

    @EmbeddedId
    private RecipeIngredientId id;

    @ManyToOne
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    @Column(nullable = false, length = 50)
    private String quantity;

    
    @Autowired
    private IngredientRepository ingredientRepository;

<<<<<<< HEAD

    public static class RecipeIngredientId implements Serializable {
        
        private Long recipeId;
        private Long ingredientId;


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RecipeIngredientId that = (RecipeIngredientId) o;
            return Objects.equals(recipeId, that.recipeId) && Objects.equals(ingredientId, that.ingredientId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(recipeId, ingredientId);
        }

=======
    public void setIngredient(String name) {
        this.ingredient = ingredientRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Ingredient not found: " + name));
>>>>>>> 7c7b34bd84b35458d4e52b02a4d76aab084129a6
    }
    


}

