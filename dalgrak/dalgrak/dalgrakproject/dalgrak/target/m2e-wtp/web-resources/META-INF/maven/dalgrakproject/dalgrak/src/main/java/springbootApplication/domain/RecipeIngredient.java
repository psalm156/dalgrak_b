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

    public void setIngredient(String name) {
        this.ingredient = ingredientRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Ingredient not found: " + name));
    }
    


}
