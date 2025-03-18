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

import java.util.Arrays;
import java.util.List;
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
        communityPost.setPostid(1L);
        communityPost.setAuthor(author);
        communityPost.setPostType(CommunityPostType.FREE);
        communityPost.setTitle("Test Post Title");
        communityPost.setContent("Test content for the community post.");
    }

    // 게시물 작성 테스트
    @Test
    void createPost_Success() {
        // Mocking
        when(communityPostRepository.save(any(CommunityPost.class))).thenReturn(communityPost);

        // 실제 메서드 실행
        CommunityPost savedPost = communityPostService.createPost(communityPost);

        // 결과 확인
        assertNotNull(savedPost);
        assertEquals(communityPost.getPostid(), savedPost.getPostid());
        verify(communityPostRepository).save(any(CommunityPost.class));  // save() 메서드 호출 확인
    }

    // 게시물 수정 테스트
    @Test
    void updatePost_Success() {
        // Mocking
        when(communityPostRepository.findById(1L)).thenReturn(Optional.of(communityPost));
        when(communityPostRepository.save(any(CommunityPost.class))).thenReturn(communityPost);

        // 실제 메서드 실행
        communityPost.setContent("Updated content");
        CommunityPost updatedPost = communityPostService.updatePost(1L, communityPost, 1L);

        // 결과 확인
        assertNotNull(updatedPost);
        assertEquals("Updated content", updatedPost.getContent());
        verify(communityPostRepository).save(any(CommunityPost.class));  // save() 메서드 호출 확인
    }

    // 작성자가 아닌 사용자가 게시물 수정 시 예외 처리 테스트
    @Test
    void updatePost_Failure_DifferentUser() {
        // Mocking
        when(communityPostRepository.findById(1L)).thenReturn(Optional.of(communityPost));

        // 예외 발생 확인
        assertThrows(RuntimeException.class, () -> communityPostService.updatePost(1L, communityPost, 2L));
    }

    // 게시물 삭제 테스트
    @Test
    void deletePost_Success() {
        // Mocking
        when(communityPostRepository.findById(1L)).thenReturn(Optional.of(communityPost));

        // 실제 메서드 실행
        communityPostService.deletePost(1L, 1L);

        // 결과 확인
        verify(communityPostRepository).delete(any(CommunityPost.class));  // delete() 메서드 호출 확인
    }

    // 작성자가 아닌 사용자가 게시물 삭제 시 예외 처리 테스트
    @Test
    void deletePost_Failure_DifferentUser() {
        // Mocking
        when(communityPostRepository.findById(1L)).thenReturn(Optional.of(communityPost));

        // 예외 발생 확인
        assertThrows(RuntimeException.class, () -> communityPostService.deletePost(1L, 2L));
    }

    // 게시물 제목으로 검색 테스트
    public Optional<List<CommunityPost>> getPostsByTitle(String title) { 
        List<CommunityPost> posts = communityPostRepository.findByTitleContaining(title);
        
        // posts가 비어있으면 Optional.empty()를 반환하고, 아니면 Optional.of(posts) 반환
        return posts.isEmpty() ? Optional.empty() : Optional.of(posts);
    }
}