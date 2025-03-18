package springbootApplication.service;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import springbootApplication.domain.Comment;
import springbootApplication.domain.CommunityPost;
import springbootApplication.domain.User;
import springbootApplication.repository.CommentRepository;
import springbootApplication.repository.CommunityPostRepository;
import springbootApplication.repository.SubscriptionRepository;
import springbootApplication.repository.UserRepository;
import springbootApplication.service.WebPushService;
import springbootApplication.domain.Subscription;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommunityPostRepository communityPostRepository;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final WebPushService webPushService;

    // 특정 게시글의 댓글 가져오기
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByCommunityPostId(postId);
    }

    // 댓글 추가 (구독자 푸시 알림 포함)
    @Transactional
    public void addComment(Long postId, Long userId, String content) {
        // postId와 userId를 이용해 실제 객체 가져오기
        CommunityPost post = communityPostRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 댓글 생성 및 저장
        Comment comment = new Comment();
        comment.setCommunityPost(post);
        comment.setUser(user);
        comment.setContent(content);
        commentRepository.save(comment);

        // 해당 게시글을 구독한 사용자들에게 푸시 알림 보내기
        List<Subscription> subscriptions = subscriptionRepository.findByPostId(postId);
        if (!subscriptions.isEmpty()) {
            for (Subscription subscription : subscriptions) {
                String message = user.getUsername() + "님이 댓글을 달았습니다.";
                webPushService.sendPushNotification(subscription, message);
            }
        }
    }

    // 댓글 삭제 (본인만 가능하도록)
    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("You can only delete your own comment!");
        }

        commentRepository.delete(comment);
    }
}

