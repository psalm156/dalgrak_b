package springbootApplication.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.Subscription;
import nl.martijndwars.webpush.PushService;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class WebPushService {

    private final String publicKey;
    private final String privateKey;
    private final String subject;
    private final PushService pushService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public WebPushService(
            @Value("${vapid.publicKey}") String publicKey,
            @Value("${vapid.privateKey}") String privateKey,
            @Value("${vapid.subject}") String subject) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.subject = subject;
        this.pushService = new PushService(publicKey, privateKey, subject); // PushService 재사용
    }

    private Optional<Subscription> convertToWebPushSubscription(springbootApplication.domain.Subscription customSubscription) {
        try {
            String jsonString = objectMapper.writeValueAsString(customSubscription);
            return Optional.of(objectMapper.readValue(jsonString, Subscription.class));
        } catch (Exception e) {
            System.err.println("Subscription 변환 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public void sendPushNotification(springbootApplication.domain.Subscription subscription, String message) {
        convertToWebPushSubscription(subscription).ifPresentOrElse(
            webPushSubscription -> {
                try {
                    Notification notification = new Notification(webPushSubscription, message);
                    HttpResponse response = pushService.send(notification);

                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode >= 200 && statusCode < 300) {
                        System.out.println("푸시 알림 전송 성공: " + response.getStatusLine().getReasonPhrase());
                    } else {
                        System.out.println("푸시 알림 전송 실패 [" + statusCode + "]: " + response.getStatusLine().getReasonPhrase());
                    }

                } catch (Exception e) {
                    System.err.println("푸시 알림 전송 중 오류 발생: " + e.getMessage());
                    e.printStackTrace();
                }
            },
            () -> System.out.println("Subscription 변환 실패, 푸시 알림을 보낼 수 없습니다.")
        );
    }
}
