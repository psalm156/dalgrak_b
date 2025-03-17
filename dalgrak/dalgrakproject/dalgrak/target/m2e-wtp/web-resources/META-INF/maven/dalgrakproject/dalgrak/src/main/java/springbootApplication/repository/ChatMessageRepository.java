package springbootApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springbootApplication.model.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
