package springbootApplication.domain;

<<<<<<< HEAD
import jakarta.persistence.*; 
import java.util.List;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
=======
import jakarta.persistence.*;
import java.util.List; 
import java.util.ArrayList;
>>>>>>> 7c7b34bd84b35458d4e52b02a4d76aab084129a6
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String instructions;

    @Column(nullable = false)
    private int preparationTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;
    

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeStep> steps = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeIngredient> ingredients = new ArrayList<>();
    
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Timer> timers = new ArrayList<>();

    public void addTimer(Timer timer) {
        this.timers.add(timer);
        timer.setRecipe(this);
    }
}

