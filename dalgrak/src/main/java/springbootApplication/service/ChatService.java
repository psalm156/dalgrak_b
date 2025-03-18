package springbootApplication.service;

import org.springframework.stereotype.Service;
import springbootApplication.dto.ChatMessageDto;
import springbootApplication.model.ChatMessage;
import springbootApplication.repository.ChatMessageRepository;

@Service
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    public void saveMessage(ChatMessageDto chatMessageDto) {
        ChatMessage message = ChatMessage.builder()
                .sender(chatMessageDto.getSender())
                .message(chatMessageDto.getMessage())
                .build();
        chatMessageRepository.save(message);
    }
}
