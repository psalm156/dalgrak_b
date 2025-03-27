package springbootApplication.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import springbootApplication.domain.Comment;
import springbootApplication.domain.CommunityPost;
import springbootApplication.domain.User;
import springbootApplication.repository.CommentRepository;
import springbootApplication.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WebPushService webPushService;

    private Comment comment;
    private User user;
    private CommunityPost post;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);
        user.setPushNotificationEndpoint("endpoint");
        user.setPushNotificationAuth("auth");
        user.setPushNotificationP256dh("p256dh");

        comment = new Comment(1L, "Test Comment", post, user, LocalDateTime.now());
    }

    @Test // 특정 게시글의 댓글 목록을 반환하는지 확인
    void getCommentsByPostId_shouldReturnComments() {
        // Given
        List<Comment> comments = Arrays.asList(comment);
        when(commentRepository.findByPostId(1L)).thenReturn(comments);

        // When
        List<Comment> result = commentService.getCommentsByPostId(1L);

        // Then
        assertEquals(1, result.size());
        assertEquals("Test Comment", result.get(0).getContent());
        verify(commentRepository, times(1)).findByPostId(1L);
    }

    @Test // 작성자만 삭제 가능한지 검증
    void deleteComment_shouldDeleteCommentIfUserIsOwner() {
        // Given
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        // When
        commentService.deleteComment(1L, 1L);

        // Then
        verify(commentRepository, times(1)).delete(comment);
    }

    @Test // 작성자가 아니면 예외 발생 확인 
    void deleteComment_shouldThrowExceptionIfUserIsNotOwner() {
        // Given
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        // When & Then
        Exception exception = assertThrows(RuntimeException.class, () -> 
            commentService.deleteComment(1L, 2L) // 다른 userId 사용
        );

        assertEquals("You can only delete your own comment!", exception.getMessage());
        verify(commentRepository, never()).delete(any());
    }

    @Test // 푸시 알림 전송되는지 확인
    void addComment_shouldSaveCommentAndSendNotification() {
        // Given
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        // When
        commentService.addComment(post.getPostId(), user.getUserId(), "New Comment");

        // Then
        verify(commentRepository, times(1)).save(any(Comment.class));
        verify(webPushService, times(1)).sendPushNotification(anyString(), anyString(), anyString(), anyString());
    }
}
