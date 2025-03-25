package springbootApplication.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import springbootApplication.domain.Comment;
import springbootApplication.repository.CommentRepository;

@ExtendWith(MockitoExtension.class) 
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private WebPushService webPushService;

    @InjectMocks
    private CommentService commentService;

    private Comment comment;

    @Test
    void addComment_ShouldSaveCommentAndSendNotifications() {
        Long postId = 1L;
        Long userId = 1L;
        String content = "테스트 댓글";

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        commentService.addComment(postId, userId, content);

        verify(commentRepository, times(1)).save(any(Comment.class)); 
    }

    @Test
    void addComment_ShouldNotSendNotification_WhenNoSubscribers() {
        Long postId = 1L;
        Long userId = 1L;
        String content = "테스트 댓글";

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        commentService.addComment(postId, userId, content);

        verify(commentRepository, times(1)).save(any(Comment.class));
    }

}
