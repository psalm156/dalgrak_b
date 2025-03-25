package springbootApplication.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springbootApplication.domain.CommunityPost;
import springbootApplication.domain.CommunityPostType;
import springbootApplication.domain.User;
import springbootApplication.repository.CommunityPostRepository;
import springbootApplication.service.CommunityPostService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommunityPostServiceTest {

    @Mock
    private CommunityPostRepository communityPostRepository;

    @InjectMocks
    private CommunityPostService communityPostService;

    private CommunityPost communityPost;
    private User author;

    @BeforeEach
    void setUp() {
        author = new User();
        author.setUserId(1L);
        author.setUsername("testUser");
        author.setEmail("test@example.com");

        communityPost = new CommunityPost();
        communityPost.setPostId(1L);
        communityPost.setUser(author);
        communityPost.setBoardType(CommunityPostType.FREE);
        communityPost.setTitle("Test Post Title");
        communityPost.setContent("Test content for the community post.");
    }

    // 게시물 ID 및 BoardType으로 조회 테스트
    @Test
    void getPostByIdAndBoardType_Success() {
        // Mocking: ID와 BoardType이 일치하는 게시물 반환
        when(communityPostRepository.findByIdAndBoardType(1L, CommunityPostType.FREE))
                .thenReturn(Optional.of(communityPost));

        // 서비스 호출
        Optional<CommunityPost> result = communityPostService.getPostByIdAndBoardType(1L, "FREE");

        // 검증
        assertTrue(result.isPresent());
        assertEquals(communityPost.getPostId(), result.get().getPostId());
        verify(communityPostRepository).findByIdAndBoardType(1L, CommunityPostType.FREE);
    }

    // 존재하지 않는 boardType 요청 시 예외 발생 테스트
    @Test
    void getPostByIdAndBoardType_InvalidBoardType() {
        // 잘못된 boardType을 전달했을 때 예외 발생 여부 확인
        Exception exception = assertThrows(RuntimeException.class, () ->
                communityPostService.getPostByIdAndBoardType(1L, "INVALID_TYPE"));

        assertEquals("Invalid board type: INVALID_TYPE", exception.getMessage());
    }
}
