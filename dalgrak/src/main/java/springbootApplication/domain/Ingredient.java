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


    // Ingredient와 RecipeIngredient 사이의 관계를 설정
    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredient> recipes = new ArrayList<>();

    // 기본 생성자 (선택사항)
    public Ingredient() {
    }

    // 생성자
    public Ingredient(String name) {
        this.name = name;
    }
}
