package springbootApplication.domain;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "timers")
public class Timer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "step_id")
    private RecipeStep step;
    
    @Column(nullable = false)
    private int remainingTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TimerStatus status = TimerStatus.RUNNING;
}