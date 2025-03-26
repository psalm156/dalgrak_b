package springbootApplication.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    // 푸시 알림을 보낼 메서드
    public void sendPushNotification(String endpoint, String message, String auth, String p256dh) {
        try {
            // Notification 객체를 생성
            Notification notification = new Notification(endpoint, auth, p256dh, message);

            // 푸시 알림을 전송하는 PushService 객체 생성
            PushService pushService = new PushService(publicKey, privateKey, subject);

            // 알림 전송
            HttpResponse response = pushService.send(notification);

            // 전송 결과 확인
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
