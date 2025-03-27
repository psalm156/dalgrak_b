package springbootApplication.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recipe_ingredients")
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 복합 키 대신 단일 ID 사용

    @Column(nullable = false)
    private String ingredientName;  // Ingredient 대신 문자열

    @Column(nullable = false)
    private String quantity;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    public RecipeIngredient(String ingredientName, String quantity, Recipe recipe) {
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.recipe = recipe;
    }
}
