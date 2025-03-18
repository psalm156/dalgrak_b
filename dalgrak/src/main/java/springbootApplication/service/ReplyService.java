package springbootApplication.service;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootApplication.domain.Reply;
import springbootApplication.repository.ReplyRepository;
import springbootApplication.repository.SubscriptionRepository;
import springbootApplication.service.WebPushService;
import springbootApplication.domain.Subscription;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final WebPushService webPushService;

    @Transactional
    public void addReply(Long commentId, Long userId, String content) {
        Reply reply = new Reply(commentId, userId, content);
        replyRepository.save(reply);

        List<Subscription> subscriptions = subscriptionRepository.findByCommentId(commentId);

        if (subscriptions != null && !subscriptions.isEmpty()) {
            for (Subscription subscription : subscriptions) {
                String message = userId + "님이 답글을 달았습니다.";
                webPushService.sendPushNotification(subscription, message);
            }
        }
    }
}
