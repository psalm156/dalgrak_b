package springbootApplication.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "favorites")
public class Favorite {

    @EmbeddedId
    private FavoriteId id = new FavoriteId();

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @ManyToOne
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipeId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    // 기본 생성자, Getter, Setter 메서드 등

    public Recipe getRecipe() {
        return recipe;
    }

  
    public User getUser() {
        return user;
    }

    @Embeddable
    public static class FavoriteId implements Serializable {

        @Column(name = "user_id")
        private Long userId;

        @Column(name = "recipe_id")
        private Long recipeId;

        // 기본 생성자, Getter, Setter 메서드

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FavoriteId that = (FavoriteId) o;
            return userId.equals(that.userId) && recipeId.equals(that.recipeId);
        }

        @Override
        public int hashCode() {
            return 31 * userId.hashCode() + recipeId.hashCode();
            
        }
    }
}

