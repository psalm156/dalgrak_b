package springbootApplication.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springbootApplication.dto.ChatMessageDto;
import springbootApplication.model.ChatMessage;
import springbootApplication.repository.ChatMessageRepository;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @Mock
    private ChatMessageRepository chatMessageRepository;

    @InjectMocks
    private ChatService chatService;

    private ChatMessageDto chatMessageDto;

    @BeforeEach
    void setUp() {
        chatMessageDto = new ChatMessageDto();
        chatMessageDto.setSender("User1");
        chatMessageDto.setMessage("Hello, this is a test message.");
    }

    @Test
    void testSaveMessage() {
        chatService.saveMessage(chatMessageDto);

        verify(chatMessageRepository, times(1)).save(any(ChatMessage.class)); // 변경된 부분
    }
}

