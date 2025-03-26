package springbootApplication.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springbootApplication.domain.User;
import springbootApplication.repository.UserRepository;
import springbootApplication.service.ScheduledTaskService;
import springbootApplication.service.WebPushService;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ScheduledTaskServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private WebPushService webPushService;

    @InjectMocks
    private ScheduledTaskService scheduledTaskService;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = User.builder()
                .userId(1L)
                .pushNotificationEndpoint("endpoint1")
                .pushNotificationAuth("auth1")
                .pushNotificationP256dh("p256dh1")
                .build();

        user2 = User.builder()
                .userId(2L)
                .pushNotificationEndpoint("endpoint2")
                .pushNotificationAuth("auth2")
                .pushNotificationP256dh("p256dh2")
                .build();
    }

    @Test
    void sendWeeklyMenuRecommendation_ShouldSendNotificationToAllUsers() {
        // Given
        List<User> userList = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(userList);

        // When
        scheduledTaskService.sendWeeklyMenuRecommendation();

        // Then
        verify(userRepository, times(1)).findAll();  // userRepository.findAll()가 호출된 횟수 확인
        verify(webPushService, times(1)).sendPushNotification("endpoint1", "오늘의 메뉴 추천: 일주일에 한 번씩!", "auth1", "p256dh1");
        verify(webPushService, times(1)).sendPushNotification("endpoint2", "오늘의 메뉴 추천: 일주일에 한 번씩!", "auth2", "p256dh2");
    }
}

