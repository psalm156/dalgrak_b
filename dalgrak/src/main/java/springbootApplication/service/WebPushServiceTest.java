package springbootApplication.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)  // lenient 모드로 설정
public class WebPushServiceTest {

    @Mock
    private CustomPushService customPushService;

    @InjectMocks
    private WebPushService webPushService;

    @Mock
    private HttpResponse mockResponse;

    @Mock
    private StatusLine statusLine;

    @Test
    public void sendPushNotification_ShouldSendNotificationSuccessfully() {
        // Given
        String endpoint = "http://example.com/endpoint";
        String message = "Test Push Notification";
        String auth = "auth";
        String p256dh = "p256dh";

        // 상태 코드가 201로 설정 (성공적인 경우)
        when(statusLine.getStatusCode()).thenReturn(201);  
        when(mockResponse.getStatusLine()).thenReturn(statusLine);  
        when(customPushService.send(endpoint, message, auth, p256dh)).thenReturn(mockResponse);

        // When
        webPushService.sendPushNotification(endpoint, message, auth, p256dh);  

        // Then
        verify(customPushService, times(1)).send(endpoint, message, auth, p256dh);  
    }

    @Test
    public void sendPushNotification_ShouldFailIfResponseNot201() {
        // Given
        String endpoint = "http://example.com/endpoint";
        String message = "Test Push Notification";
        String auth = "auth";
        String p256dh = "p256dh";

        // 상태 코드가 400으로 설정 (실패한 경우)
        when(statusLine.getStatusCode()).thenReturn(400);  
        when(mockResponse.getStatusLine()).thenReturn(statusLine);  
        when(customPushService.send(endpoint, message, auth, p256dh)).thenReturn(mockResponse);

        // When
        webPushService.sendPushNotification(endpoint, message, auth, p256dh);  

        // Then
        verify(customPushService, times(1)).send(endpoint, message, auth, p256dh);  
    }
}
