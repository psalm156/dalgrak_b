package springbootApplication.service;

import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WebPushService { //custhompushService로직을 사용하여 푸시 알림을 보냄

    private final String publicKey;
    private final String privateKey;
    private final String subject;
    private final CustomPushService customPushService;  

    public WebPushService(
            @Value("${vapid.publicKey}") String publicKey,
            @Value("${vapid.privateKey}") String privateKey,
            @Value("${vapid.subject}") String subject,
            CustomPushService customPushService) { 
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.subject = subject;
        this.customPushService = customPushService;
    }

    // 푸시 알림을 보내는 메서드
    public void sendPushNotification(String endpoint, String message, String auth, String p256dh) {
        HttpResponse response = customPushService.send(endpoint, message, auth, p256dh);
        // 응답 상태 코드 체크 등 추가 로직
    }
}
