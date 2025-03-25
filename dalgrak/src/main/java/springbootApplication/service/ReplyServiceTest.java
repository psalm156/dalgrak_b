package springbootApplication.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springbootApplication.domain.Reply;
import springbootApplication.repository.ReplyRepository;
import springbootApplication.service.WebPushService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReplyServiceTest {

    @Mock
    private ReplyRepository replyRepository;

    @Mock
    private WebPushService webPushService;

    @InjectMocks
    private ReplyService replyService;

    private Long commentId;
    private Long userId;
    private String content;

    @BeforeEach
    void setUp() {
        commentId = 1L;
        userId = 1L;
        content = "This is a test reply!";
    }

    @Test
    void testAddReply_NoSubscriptions() {

        replyService.addReply(commentId, userId, content);

        verify(replyRepository, times(1)).save(any(Reply.class));
    }

}
