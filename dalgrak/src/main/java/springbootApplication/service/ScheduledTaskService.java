package springbootApplication.service;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import springbootApplication.repository.SubscriptionRepository;
import springbootApplication.service.WebPushService;
import springbootApplication.domain.Subscription;


@Service
@RequiredArgsConstructor
public class ScheduledTaskService {

    private final SubscriptionRepository subscriptionRepository;
    private final WebPushService webPushService;

    @Scheduled(cron = "0 0 9 * * MON")
    public void sendWeeklyMenuRecommendation() {
        List<Subscription> allSubscribers = subscriptionRepository.findAll();

        for (Subscription sub : allSubscribers) {
            String message = "오늘의 메뉴 추천: 일주일에 한 번씩!";
            webPushService.sendPushNotification(sub, message);
        }
    }
}
