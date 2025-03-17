package springbootApplication.domain;

import lombok.*;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

<<<<<<< HEAD
=======

>>>>>>> 7c7b34bd84b35458d4e52b02a4d76aab084129a6
    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredient> recipes = new ArrayList<>();

    public Ingredient() {
    }

    public Ingredient(String name) {
        this.name = name;
    }
}
