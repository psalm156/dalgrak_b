package springbootApplication.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "favorites")
public class Favorite {

    @EmbeddedId
    private FavoriteId id = new FavoriteId();

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();


    @Embeddable
    public static class FavoriteId implements Serializable {

        @Column(name = "user_id")
        private Long userId;

        @Column(name = "recipe_id")
        private Long recipeId;


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

