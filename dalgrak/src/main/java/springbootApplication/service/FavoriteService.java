package springbootApplication.service;

import springbootApplication.domain.Favorite;
import springbootApplication.repository.FavoriteRepository;
import springbootApplication.repository.ReplyRepository;
import springbootApplication.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final WebPushService webPushService; 


    public List<Favorite> getAllFavorites() {
        return favoriteRepository.findAll();
    }

    public Optional<Favorite> getFavoriteByUser_IdAndRecipe_Id(Long userId, Long recipeId) {
        return favoriteRepository.findByUserIdAndRecipeId(userId, recipeId);
    }

    @Transactional
    public Favorite addFavorite(Favorite favorite) {
        Favorite savedFavorite = favoriteRepository.save(favorite);

        return savedFavorite;
    }

    @Transactional
    public void removeFavorite(Long userId, Long recipeId) {
        favoriteRepository.deleteByUserIdAndRecipeId(userId, recipeId);
    }
}

