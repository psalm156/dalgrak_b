package springbootApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springbootApplication.domain.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    // 특정 사용자가 구독한 포스트 조회
    Optional<Subscription> findByUserId(Long userId);

    // 특정 포스트에 구독한 사용자 조회
    List<Subscription> findByPostId(Long postId);

    // 모든 구독 조회
    List<Subscription> findAll();
    List<Subscription> findByRecipeId(Long recipeId); 
    List<Subscription> findByCommentId(Long commentId);
}
