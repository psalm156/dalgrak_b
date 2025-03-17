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
<<<<<<< HEAD
    @JoinColumn(name = "step_id")
=======
    @JoinColumn(name = "step_id", nullable = false)
>>>>>>> 7c7b34bd84b35458d4e52b02a4d76aab084129a6
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