package springbootApplication.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class FavoriteId implements Serializable {

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