package springbootApplication.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springbootApplication.domain.Comment;
import springbootApplication.domain.Reply;
import springbootApplication.domain.User;
import springbootApplication.repository.CommentRepository;
import springbootApplication.repository.ReplyRepository;
import springbootApplication.repository.UserRepository;
import springbootApplication.service.WebPushService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ReplyServiceTest {

    @Mock
    private ReplyRepository replyRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WebPushService webPushService;

    @InjectMocks
    private ReplyService replyService;

    private Comment comment;
    private User user;
    private Reply reply;

    @BeforeEach
    void setUp() {
        // 댓글 객체 생성
        comment = new Comment();
        comment.setCommentId(1L);
        comment.setContent("This is a comment");
        comment.setPost(2L);  // 해당 포스트 ID

        // 사용자 객체 생성
        user = User.builder()
                .userId(2L)
                .pushNotificationEndpoint("endpoint")
                .pushNotificationAuth("auth")
                .pushNotificationP256dh("p256dh")
                .build();

        // 댓글에 사용자 객체 설정
        comment.setUser(user);

        // 댓글에 User 객체 설정 후 Reply 객체 생성
        reply = new Reply();
        reply.setReplyId(1L);
        reply.setComment(comment);  // 댓글과 연결
        reply.setUser(user);  // 작성자 설정
        reply.setContent("This is a reply");
    }

    @Test
    void getRepliesByCommentId_ShouldReturnReplies() {
        // Given
        Long commentId = 1L;
        when(replyRepository.findByParentCommentId(commentId)).thenReturn(Arrays.asList(reply));

        // When
        List<Reply> result = replyService.getRepliesByCommentId(commentId);

        // Then
        assertEquals(1, result.size());
        assertEquals("This is a reply", result.get(0).getContent());
        verify(replyRepository, times(1)).findByParentCommentId(commentId);
    }

    @Test
    void deleteReply_ShouldDeleteReplyIfUserIsAuthor() {
        // Given
        Long replyId = 1L;
        Long userId = 3L;
        when(replyRepository.findById(replyId)).thenReturn(Optional.of(reply));

        // When
        replyService.deleteReply(replyId, userId);

        // Then
        verify(replyRepository, times(1)).delete(reply);
    }

    @Test
    void deleteReply_ShouldThrowExceptionIfUserIsNotAuthor() {
        // Given
        Long replyId = 1L;
        Long userId = 2L;  // 다른 사용자
        when(replyRepository.findById(replyId)).thenReturn(Optional.of(reply));

        // When & Then
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            replyService.deleteReply(replyId, userId);
        });
        assertEquals("You can only delete your own reply!", thrown.getMessage());
    }

    @Test
    void addReply_ShouldSaveReplyAndSendNotificationToCommentAuthor() {
        // Given
        Long commentId = 1L;
        Long userId = 3L;
        String content = "This is a reply";
        
        // 댓글 객체 생성
        Comment comment = new Comment();
        comment.setCommentId(commentId);
        comment.setContent("This is a comment");
        comment.setPost(2L);
        comment.setUser(user); // 댓글 작성자 설정
        
        // 사용자 객체 생성
        User user = User.builder()
                .userId(userId)
                .pushNotificationEndpoint("endpoint")
                .pushNotificationAuth("auth")
                .pushNotificationP256dh("p256dh")
                .build();
        
        // Reply 객체 생성
        Reply newReply = new Reply();
        newReply.setContent(content);
        newReply.setComment(comment);  // 댓글 연결
        newReply.setUser(user);        // 사용자 설정

        // 댓글과 사용자 객체를 설정
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        when(replyRepository.save(any(Reply.class))).thenReturn(newReply);
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));  // 댓글 작성자에 해당하는 사용자

        // When
        replyService.addReply(commentId, userId, content);

        // Then
        verify(replyRepository, times(1)).save(any(Reply.class));
        verify(webPushService, times(1)).sendPushNotification(
                "endpoint", "Someone replied to your comment: This is a reply", "auth", "p256dh"
        );
    }

}
