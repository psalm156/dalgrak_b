package springbootApplication.service;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootApplication.domain.Comment;
import springbootApplication.repository.CommentRepository;
import springbootApplication.repository.SubscriptionRepository;
import springbootApplication.service.WebPushService;
import springbootApplication.domain.Subscription;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final WebPushService webPushService;

    @Transactional
    public void addComment(Long postId, Long userId, String content) {
        Comment comment = new Comment(postId, userId, content);
        commentRepository.save(comment);

        List<Subscription> subscriptions = subscriptionRepository.findByCommentId(comment.getId());
        
        // 구독한 모든 사용자에게 푸시 알림 전송
        if (subscriptions != null && !subscriptions.isEmpty()) {
        for (Subscription subscription : subscriptions) {
            String message = userId + "님이 댓글을 달았습니다.";
            webPushService.sendPushNotification(subscription, message);
        	}
        }
    }
}

