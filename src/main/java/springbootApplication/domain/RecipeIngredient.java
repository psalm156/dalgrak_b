package springbootApplication.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "recipe_ingredients")
@IdClass(RecipeIngredient.RecipeIngredientId.class)  // @IdClass로 복합 키를 정의
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "recipe_id", updatable = false)
    private Recipe recipe;

    @JoinColumn(name = "ingredient_id", insertable = false, updatable = false)
    private String ingredient;

    @Column(nullable = false, length = 50)
    private String quantity;



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

    }
}


