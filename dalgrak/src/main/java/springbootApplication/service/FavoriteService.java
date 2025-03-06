package springbootApplication.service;

import springbootApplication.domain.Favorite;
import springbootApplication.domain.Subscription;
import springbootApplication.repository.FavoriteRepository;
import springbootApplication.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final WebPushService webPushService; 

    public FavoriteService(FavoriteRepository favoriteRepository, 
                           SubscriptionRepository subscriptionRepository, 
                           WebPushService webPushService) {
        this.favoriteRepository = favoriteRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.webPushService = webPushService; 
    }

    public List<Favorite> getAllFavorites() {
        return favoriteRepository.findAll();
    }

    public Optional<Favorite> getFavoriteByUser_IdAndRecipe_Id(Long userId, Long recipeId) {
        return favoriteRepository.findByUser_IdAndRecipe_Id(userId, recipeId);
    }

    @Transactional
    public Favorite addFavorite(Favorite favorite) {
        Favorite savedFavorite = favoriteRepository.save(favorite);

        // 즐겨찾기 추가 시, 해당 레시피에 구독한 사용자들에게 알림 전송
        List<Subscription> subscribers = subscriptionRepository.findByRecipeId(favorite.getRecipe().getId());
        for (Subscription subscriber : subscribers) {
            String message = favorite.getUser().getUsername() + "님이 즐겨찾기에 추가했습니다.";
            webPushService.sendPushNotification(subscriber, message);
        }

        return savedFavorite;
    }

    @Transactional
    public void removeFavorite(Long userId, Long recipeId) {
        favoriteRepository.deleteByUser_IdAndRecipe_Id(userId, recipeId);
        List<Subscription> subscribers = subscriptionRepository.findByRecipeId(recipeId);
        for (Subscription subscriber : subscribers) {
            String message = userId + "님이 즐겨찾기를 삭제했습니다.";
            webPushService.sendPushNotification(subscriber, message);
        }
    }
}


