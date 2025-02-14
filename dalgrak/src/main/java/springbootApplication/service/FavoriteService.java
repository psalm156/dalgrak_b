package springbootApplication.service;

import springbootApplication.domain.Favorite;
import springbootApplication.repository.FavoriteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public List<Favorite> getAllFavorites() {
        return favoriteRepository.findAll();
    }

    public Optional<Favorite> getFavoriteByUser_IdAndRecipe_Id(Long userId, Long recipeId) {
        return favoriteRepository.findByUser_IdAndRecipe_Id(userId, recipeId);
    }

    public Favorite addFavorite(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    public void removeFavorite(Long userId, Long recipeId) {
        favoriteRepository.deleteByUser_IdAndRecipe_Id(userId, recipeId);
    }
}

