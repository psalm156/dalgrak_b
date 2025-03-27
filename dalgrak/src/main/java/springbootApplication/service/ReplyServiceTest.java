package springbootApplication.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springbootApplication.domain.Comment;
import springbootApplication.domain.CommunityPost;
import springbootApplication.domain.Reply;
import springbootApplication.domain.User;
import springbootApplication.repository.CommentRepository;
import springbootApplication.repository.ReplyRepository;
import springbootApplication.repository.UserRepository;
import springbootApplication.service.WebPushService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReplyServiceTest {

    @InjectMocks
    private ReplyService replyService;

    @Mock
    private ReplyRepository replyRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WebPushService webPushService;

    private Reply reply;
    private Comment comment;
    private User user;
    CommunityPost post = new CommunityPost(); 

    @BeforeEach
    void setUp() {
        // 공통 테스트 데이터 초기화
        comment = new Comment(1L, user, post, "Test Comment");
        // 1L = postId, 100L = userId, 200L = id, "Test Comment" = content
        
        // Reply 생성자 사용 (userId, content, comment)
        reply = new Reply(user, "Test Reply", comment);
        
        user = User.builder()
                .userId(100L)     // Reply와 Comment의 userId와 일치
                .username("testUser")
                .email("test@example.com")
                .password("password")
                .pushNotificationEndpoint("endpoint")
                .pushNotificationAuth("auth")
                .pushNotificationP256dh("p256dh")
                .build();
    }

    // 특정 댓글의 답글 가져오기 테스트
    @Test
    void getRepliesByCommentId_shouldReturnReplies() {
        // 수정: findByCommentId -> findByParentCommentId
        when(replyRepository.findByParentCommentId(200L)).thenReturn(List.of(reply));
        
        List<Reply> replies = replyService.getRepliesByCommentId(200L);
        
        assertEquals(1, replies.size());
        assertEquals("Test Reply", replies.get(0).getContent());
        
        // 검증 부분도 수정
        verify(replyRepository).findByParentCommentId(200L);
    }

    // 답글 삭제 (본인만 가능)
    @Test
    void deleteReply_shouldDeleteReplyWhenUserMatches() {
        when(replyRepository.findById(1L)).thenReturn(Optional.of(reply));

        replyService.deleteReply(1L, 100L);

        verify(replyRepository).delete(reply);
    }

    // 답글 삭제 실패 (잘못된 유저가 삭제 시도)
    @Test
    void deleteReply_shouldThrowExceptionWhenUserDoesNotMatch() {
        when(replyRepository.findById(1L)).thenReturn(Optional.of(reply));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> 
            replyService.deleteReply(1L, 999L) // 잘못된 userId
        );

        assertEquals("You can only delete your own reply!", thrown.getMessage());
        verify(replyRepository, never()).delete(any());
    }

    // 답글 추가 (댓글이 존재하는 경우)
    @Test
    void addReply_shouldSaveReplyAndSendNotification() {
        // 댓글과 사용자 정보에 대한 스텁 설정
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));  // 댓글 존재 여부
        when(userRepository.findById(100L)).thenReturn(Optional.of(user));  // 유저 존재 여부

        // 답글 추가 메서드 실행 - void 메소드라면 반환값을 받지 않도록 수정
        replyService.addReply(1L, 100L, "New Reply");

        // 스텁 호출 검증
        verify(replyRepository).save(any(Reply.class));  // 답글 저장 확인
        verify(webPushService).sendPushNotification(
                eq("endpoint"), contains("New Reply"), eq("auth"), eq("p256dh")  // 푸시 알림 호출 확인
        );
    }

    // 답글 추가 실패 (댓글이 존재하지 않는 경우)
    @Test
    void addReply_shouldThrowExceptionWhenCommentNotFound() {
        // 댓글이 존재하지 않는 경우에 대한 스텁 설정
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());  // 댓글이 없음

        // 예외가 발생하는지 확인
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> 
            replyService.addReply(1L, 100L, "New Reply")  // 댓글이 없으므로 예외가 발생해야 함
        );

        // 예외 메시지 확인
        assertEquals("Comment not found", thrown.getMessage());

        // save 메서드가 호출되지 않아야 하므로 verify로 확인
        verify(replyRepository, never()).save(any());  // replyRepository.save()가 호출되지 않아야 함
    }  
}
