package springbootApplication.domain;

import jakarta.persistence.*;  // JPA 애너테이션을 사용하기 위한 import
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "recipe_steps")
public class RecipeStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @Column(nullable = false)
    private int stepNumber;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String instruction;

    @Column(nullable = true)
    private Integer estimatedTime; // 분 단위

    // Getter, Setter, Constructor 등이 필요하면 추가
}
