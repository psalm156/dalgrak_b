package springbootApplication.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.Subscription;
import nl.martijndwars.webpush.PushService;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class WebPushService {

    private final String publicKey;
    private final String privateKey;
    private final String subject;

    public WebPushService(
            @Value("${vapid.publicKey}") String publicKey,
            @Value("${vapid.privateKey}") String privateKey,
            @Value("${vapid.subject}") String subject) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.subject = subject;
    }


    Subscription convertToWebPushSubscription(springbootApplication.domain.Subscription customSubscription) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(customSubscription);
            return objectMapper.readValue(jsonString, Subscription.class);
        } catch (Exception e) {
            System.err.println("Subscription 변환 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void sendPushNotification(springbootApplication.domain.Subscription subscription, String message) {
        try {
            Subscription webPushSubscription = convertToWebPushSubscription(subscription);
            if (webPushSubscription == null) {
                System.out.println("Subscription 변환 실패");
                return;
            }

            byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
            Notification notification = new Notification(webPushSubscription, message);

            PushService pushService = new PushService(publicKey, privateKey, subject);
            HttpResponse response = pushService.send(notification);

            if (response.getStatusLine().getStatusCode() == 201) {
                System.out.println("푸시 알림 전송 성공: " + response.getStatusLine().getReasonPhrase());
            } else {
                System.out.println("푸시 알림 전송 실패: " + response.getStatusLine().getReasonPhrase());
            }

        } catch (Exception e) {
            System.err.println("푸시 알림 전송 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
