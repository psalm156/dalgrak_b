package springbootApplication.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springbootApplication.domain.Reply;
import springbootApplication.domain.Subscription;
import springbootApplication.repository.ReplyRepository;
import springbootApplication.repository.SubscriptionRepository;
import springbootApplication.service.WebPushService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReplyServiceTest {

    @Mock
    private ReplyRepository replyRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private WebPushService webPushService;

    @InjectMocks
    private ReplyService replyService;

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
        when(subscriptionRepository.findByCommentId(commentId)).thenReturn(List.of());

        replyService.addReply(commentId, userId, content);

        verify(replyRepository, times(1)).save(any(Reply.class));
        verify(webPushService, times(0)).sendPushNotification(any(Subscription.class), any(String.class)); 
    }

    @Test
    void testAddReply_WithSubscriptions() {
        Subscription subscription = new Subscription();
        subscription.setCommentId(commentId);
        subscription.setUserId(2L);
        List<Subscription> subscriptions = Arrays.asList(subscription);

        when(subscriptionRepository.findByCommentId(commentId)).thenReturn(subscriptions);

        replyService.addReply(commentId, userId, content);

       
        verify(replyRepository, times(1)).save(any(Reply.class)); 
        verify(webPushService, times(1)).sendPushNotification(eq(subscription), eq(userId + "님이 답글을 달았습니다.")); // WebPushService의 sendPushNotification 호출 검증
    }
}
