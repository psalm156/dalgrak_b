package springbootApplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import springbootApplication.domain.Comment;
import springbootApplication.repository.CommentRepository;
import springbootApplication.repository.UserRepository;
import springbootApplication.service.WebPushService;
import springbootApplication.domain.User;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final WebPushService webPushService;

    // 특정 게시글의 댓글 가져오기
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    // 댓글 삭제 (본인만 가능)
    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        if (!comment.getUserId().equals(userId)) {
            throw new RuntimeException("You can only delete your own comment!");
        }

        commentRepository.delete(comment);
    }

    // 댓글 추가
    @Transactional
    public void addComment(Long postId, Long userId, String content) {
        Comment comment = new Comment(postId, userId, content);
        commentRepository.save(comment);

   }
}

        // 댓글 추가 후, 모든 사용자에게 알림 전송
        sendPushNotificationToAllUsers(content);
    }

    // 댓글 달리면 알림을 모든 사용자에게 보내는 메소드
    private void sendPushNotificationToAllUsers(String message) {
        List<User> allUsers = userRepository.findAll();  // 모든 사용자 조회
        
        for (User user : allUsers) {
            // 각 사용자의 푸시 알림 정보를 얻어야 함
            String endpoint = user.getPushNotificationEndpoint();  // 실제 endpoint를 가져오는 메소드
            String auth = user.getPushNotificationAuth();  // 실제 auth를 가져오는 메소드
            String p256dh = user.getPushNotificationP256dh();  // 실제 p256dh를 가져오는 메소드

            // 알림 전송
            webPushService.sendPushNotification(endpoint, message, auth, p256dh);
        }
    }
}

