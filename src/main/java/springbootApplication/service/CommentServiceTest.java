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
import springbootApplication.domain.Subscription;
import springbootApplication.repository.CommentRepository;
import springbootApplication.repository.SubscriptionRepository;

@ExtendWith(MockitoExtension.class) 
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private WebPushService webPushService;

    @InjectMocks
    private CommentService commentService;

    private Comment comment;
    private Subscription subscription;

    @BeforeEach
    void setUp() {
        comment = new Comment(1L, 1L, "테스트 댓글");
        subscription = new Subscription();
        

    }

    @Test
    void addComment_ShouldSaveCommentAndSendNotifications() {
        Long postId = 1L;
        Long userId = 1L;
        String content = "테스트 댓글";
        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(subscription);

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(subscriptionRepository.findByCommentId(comment.getId())).thenReturn(subscriptions);

        commentService.addComment(postId, userId, content);

        verify(commentRepository, times(1)).save(any(Comment.class)); 
        verify(subscriptionRepository, times(1)).findByCommentId(comment.getId());
        verify(webPushService, times(1)).sendPushNotification(any(Subscription.class), anyString());
    }

    @Test
    void addComment_ShouldNotSendNotification_WhenNoSubscribers() {
        Long postId = 1L;
        Long userId = 1L;
        String content = "테스트 댓글";

        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        when(subscriptionRepository.findByCommentId(eq(comment.getId()))).thenReturn(new ArrayList<>());

        commentService.addComment(postId, userId, content);

        verify(commentRepository, times(1)).save(any(Comment.class));
        verify(subscriptionRepository, times(1)).findByCommentId(eq(comment.getId())); 
        verify(webPushService, never()).sendPushNotification(any(Subscription.class), anyString());
    }

}
