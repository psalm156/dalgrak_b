package springbootApplication.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import springbootApplication.domain.Comment;
import springbootApplication.repository.CommentRepository;
import springbootApplication.repository.UserRepository;

import java.util.List;

@Service

public class CommentService {
    
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    // 특정 게시글의 댓글 가져오기
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByCommunityPostId(postId);
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
=======
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
        
        if (subscriptions != null && !subscriptions.isEmpty()) {
        for (Subscription subscription : subscriptions) {
            String message = userId + "님이 댓글을 달았습니다.";
            webPushService.sendPushNotification(subscription, message);
        	}
        }
    }
}

