package springbootApplication.domain;

import lombok.*;
import springbootApplication.repository.RecipeRepository;
import springbootApplication.repository.TimerRepository;
import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor

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
    @JoinColumn(name = "step_id", nullable = false)
    private RecipeStep step;
    
    @Column(name = "remaining_time", nullable = false)
    private int duration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TimerStatus status = TimerStatus.RUNNING;
    
    public Timer(Integer duration) {
        this.duration = duration;
    }
}