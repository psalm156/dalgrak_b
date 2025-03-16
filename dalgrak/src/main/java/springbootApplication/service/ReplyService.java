package springbootApplication.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springbootApplication.domain.Reply;
import springbootApplication.repository.ReplyRepository;
import springbootApplication.repository.UserRepository;

import java.util.List;

@Service
public class ReplyService {
    
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;

    public ReplyService(ReplyRepository replyRepository, UserRepository userRepository) {
        this.replyRepository = replyRepository;
        this.userRepository = userRepository;
    }

    // 특정 댓글의 답글 가져오기
    public List<Reply> getRepliesByCommentId(Long commentId) {
        return replyRepository.findByParentCommentId(commentId);
    }

    // 답글 삭제 (본인만 가능)
    @Transactional
    public void deleteReply(Long replyId, Long userId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new RuntimeException("Reply not found"));

        if (!reply.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("You can only delete your own reply!");
        }

        replyRepository.delete(reply);
=======
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
