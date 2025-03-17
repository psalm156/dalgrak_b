package springbootApplication.controller;

import org.springframework.web.bind.annotation.*;
import springbootApplication.dto.ChatMessageDto;
import springbootApplication.service.ChatService;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/send")
    public void sendMessage(@RequestBody ChatMessageDto chatMessageDto) {
        chatService.saveMessage(chatMessageDto);
    }
}
