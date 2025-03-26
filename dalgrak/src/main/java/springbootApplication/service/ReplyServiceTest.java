package springbootApplication.service;

<<<<<<< HEAD
=======
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

>>>>>>> f876882b60a328bcf620df82a2bea48840362985
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
<<<<<<< HEAD
import springbootApplication.domain.Reply;
import springbootApplication.repository.ReplyRepository;
=======
import springbootApplication.domain.Comment;
import springbootApplication.domain.Reply;
import springbootApplication.domain.User;
import springbootApplication.repository.CommentRepository;
import springbootApplication.repository.ReplyRepository;
import springbootApplication.repository.UserRepository;
import springbootApplication.service.ReplyService;
>>>>>>> f876882b60a328bcf620df82a2bea48840362985
import springbootApplication.service.WebPushService;

import java.util.Arrays;
import java.util.List;
<<<<<<< HEAD

import static org.mockito.Mockito.*;
=======
import java.util.Optional;
>>>>>>> f876882b60a328bcf620df82a2bea48840362985

@ExtendWith(MockitoExtension.class)
class ReplyServiceTest {

    @Mock
    private ReplyRepository replyRepository;

    @Mock
<<<<<<< HEAD
=======
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
>>>>>>> f876882b60a328bcf620df82a2bea48840362985
    private WebPushService webPushService;

    @InjectMocks
    private ReplyService replyService;

<<<<<<< HEAD
    private Long commentId;
    private Long userId;
    private String content;

    @BeforeEach
    void setUp() {
        commentId = 1L;
        userId = 1L;
        content = "This is a test reply!";
    }

    @Test
    void testAddReply_NoSubscriptions() {

        replyService.addReply(commentId, userId, content);

        verify(replyRepository, times(1)).save(any(Reply.class));
    }

=======
    private Comment comment;
    private User user;
    private Reply reply;

    @BeforeEach
    void setUp() {
        comment = new Comment(1L, 2L, "This is a comment");
        user = User.builder()
                .id(2L)
                .pushNotificationEndpoint("endpoint")
                .pushNotificationAuth("auth")
                .pushNotificationP256dh("p256dh")
                .build();
        reply = new Reply(1L, 3L, "This is a reply");

        // 댓글에 사용자 정보 설정
        comment.setUserId(2L);  // 댓글 작성자는 userId 2
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
        Reply newReply = new Reply(commentId, userId, content);

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
>>>>>>> f876882b60a328bcf620df82a2bea48840362985
}
