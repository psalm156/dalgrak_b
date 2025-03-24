package springbootApplication.service;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

@Service
public class CustomPushService { //푸시 알림 전송을 담당하는 실제 로직을 구현한 클래스
    private String publicKey;
    private String privateKey;
    private String subject;
    private CloseableHttpClient httpClient; 
    
    public CustomPushService(String publicKey, String privateKey, String subject, CloseableHttpClient httpClient) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.subject = subject;
        this.httpClient = httpClient;  // 주입받은 httpClient 사용
    }

    public HttpResponse send(String endpoint, String message, String auth, String p256dh) {
        HttpPost httpPost = new HttpPost(endpoint);
        String jsonBody = "{\"message\":\"" + message + "\",\"auth\":\"" + auth + "\",\"p256dh\":\"" + p256dh + "\"}";

        try {
            StringEntity entity = new StringEntity(jsonBody);
            httpPost.setEntity(entity);
            httpPost.setHeader("Content-Type", "application/json");

            // httpClient를 사용하여 실제 요청을 보냄
            return httpClient.execute(httpPost);  // 주입된 httpClient 사용
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

