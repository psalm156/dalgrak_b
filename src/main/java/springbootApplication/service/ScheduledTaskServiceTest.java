package springbootApplication.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springbootApplication.domain.Subscription;
import springbootApplication.repository.SubscriptionRepository;
import springbootApplication.service.WebPushService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduledTaskServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private WebPushService webPushService;

    @InjectMocks
    private ScheduledTaskService scheduledTaskService;

    private List<Subscription> subscriptions;

    @BeforeEach
    void setUp() {
        Subscription sub1 = new Subscription();
        sub1.setId(1L);
        Subscription sub2 = new Subscription();
        sub2.setId(2L);
        subscriptions = Arrays.asList(sub1, sub2);
    }

    @Test
    void testSendWeeklyMenuRecommendation() {
        when(subscriptionRepository.findAll()).thenReturn(subscriptions);

        scheduledTaskService.sendWeeklyMenuRecommendation();

        for (Subscription sub : subscriptions) {
            verify(webPushService, times(1)).sendPushNotification(sub, "오늘의 메뉴 추천: 일주일에 한 번씩!");
        }
        
        verify(subscriptionRepository, times(1)).findAll();
    }
}
