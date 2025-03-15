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
        communityPost.setPost_id(1L);
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
        assertEquals(communityPost.getPost_id(), savedPost.getPost_id());
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
    @Test
    void getPostsByTitle_Success() {
        // Mocking
        when(communityPostRepository.findByTitleContaining("Test")).thenReturn(Arrays.asList(communityPost));

        // 실제 메서드 실행
        Optional<List<CommunityPost>> posts = Optional.ofNullable(communityPostService.getPostsByTitle("Test").get());

        // 결과 확인
        assertTrue(posts.isPresent());
        assertEquals(1, posts.get().size());
        assertEquals(communityPost.getPost_id(), posts.get().get(0).getPost_id());
    }

    // 제목에 맞는 게시물이 없는 경우 테스트
    @Test
    void getPostsByTitle_NoPosts() {
        // Mocking
        when(communityPostRepository.findByTitleContaining("NonExistent")).thenReturn(Arrays.asList());

        // 실제 메서드 실행
        Optional<List<CommunityPost>> posts = Optional.ofNullable(communityPostService.getPostsByTitle("NonExistent").get());

        // 결과 확인
        assertTrue(posts.isPresent());
        assertEquals(0, posts.get().size());
    }
}