package springbootApplication.service;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CustomPushServiceTest {

    @Mock
    private CloseableHttpClient httpClient;  // httpClient를 Mock 객체로 정의

    @InjectMocks
    private CustomPushService customPushService;  // CustomPushService에 httpClient를 주입

    @BeforeEach
    void setUp() {
        // httpClient를 주입받은 CustomPushService 생성
        customPushService = new CustomPushService("publicKey", "privateKey", "subject", httpClient);
    }

    @Test
    public void testSend_SuccessfulPushNotification() throws Exception {
        // Given
        String endpoint = "http://example.com/endpoint";
        String message = "Test Push Notification";
        String auth = "auth";
        String p256dh = "p256dh";

        // 상태 코드 201 반환하도록 설정 (성공적인 응답)
        CloseableHttpResponse httpResponse = mock(CloseableHttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);
        when(statusLine.getStatusCode()).thenReturn(201);
        when(httpResponse.getStatusLine()).thenReturn(statusLine);
        when(httpClient.execute(any(HttpPost.class))).thenReturn(httpResponse);  // execute() Mock 설정

        // When
        HttpResponse response = customPushService.send(endpoint, message, auth, p256dh);

        // Then
        assertNotNull(response);
        verify(httpClient, times(1)).execute(any(HttpPost.class));  // execute() 호출 검증
        assertEquals(201, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testSend_FailedPushNotification() throws Exception {
        // Given
        String endpoint = "http://example.com/endpoint";
        String message = "Test Push Notification";
        String auth = "auth";
        String p256dh = "p256dh";

        // 상태 코드 400 반환하도록 설정 (실패한 응답)
        CloseableHttpResponse httpResponse = mock(CloseableHttpResponse.class);
        StatusLine statusLine = mock(StatusLine.class);
        when(statusLine.getStatusCode()).thenReturn(400);
        when(httpResponse.getStatusLine()).thenReturn(statusLine);
        when(httpClient.execute(any(HttpPost.class))).thenReturn(httpResponse);  // execute() Mock 설정

        // When
        HttpResponse response = customPushService.send(endpoint, message, auth, p256dh);

        // Then
        assertNotNull(response);
        verify(httpClient, times(1)).execute(any(HttpPost.class));  // execute() 호출 검증
        assertEquals(400, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testSend_ExceptionDuringRequest() throws Exception {
        // Given
        String endpoint = "http://example.com/endpoint";
        String message = "Test Push Notification";
        String auth = "auth";
        String p256dh = "p256dh";

        // HttpClient가 예외를 던지는 경우
        when(httpClient.execute(any(HttpPost.class))).thenThrow(new RuntimeException("Request failed"));

        // When
        HttpResponse response = customPushService.send(endpoint, message, auth, p256dh);

        // Then
        assertNull(response);  // 예외 발생 시 null 반환
    }
}
