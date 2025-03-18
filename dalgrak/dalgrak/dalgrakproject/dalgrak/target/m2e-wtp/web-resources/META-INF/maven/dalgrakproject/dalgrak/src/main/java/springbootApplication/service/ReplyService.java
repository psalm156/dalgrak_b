package springbootApplication.service;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springbootApplication.domain.Reply;
import springbootApplication.domain.Comment;
import springbootApplication.domain.User;
import springbootApplication.repository.ReplyRepository;
import springbootApplication.repository.CommentRepository;
import springbootApplication.repository.SubscriptionRepository;
import springbootApplication.repository.UserRepository;
import springbootApplication.service.WebPushService;
import springbootApplication.domain.Subscription;

@RequiredArgsConstructor
@Service
public class ReplyService {
    
    private final ReplyRepository replyRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final WebPushService webPushService;

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
    }

    // 답글 추가
    @Transactional
    public void addReply(Long commentId, Long userId, String content) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Reply reply = new Reply();
        reply.setParentComment(comment);
        reply.setUser(user);
        reply.setContent(content);

        replyRepository.save(reply);

        List<Subscription> subscriptions = subscriptionRepository.findByCommentId(commentId);

        if (!subscriptions.isEmpty()) {
            for (Subscription subscription : subscriptions) {
                String message = user.getUsername() + "님이 답글을 달았습니다.";
                webPushService.sendPushNotification(subscription, message);
            }
        }
    }
}
