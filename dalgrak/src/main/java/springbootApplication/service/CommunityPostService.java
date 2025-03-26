package springbootApplication.service;

import springbootApplication.domain.CommunityPost;
import springbootApplication.domain.CommunityPostType;
import springbootApplication.repository.CommunityPostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommunityPostService {

    private final CommunityPostRepository communityPostRepository;

    public CommunityPostService(CommunityPostRepository communityPostRepository) {
        this.communityPostRepository = communityPostRepository;
    }

    public List<CommunityPost> getAllPosts() {
        return communityPostRepository.findAll();
    }

    public CommunityPost createPost(CommunityPost post) {
        return communityPostRepository.save(post);
    }

    // 게시글 수정
    public CommunityPost updatePost(Long id, CommunityPost updatedPost) {
        CommunityPost post = communityPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        post.setContent(updatedPost.getContent());
        return communityPostRepository.save(post);
    }

    // 게시글 삭제
    public void deletePost(Long id) {
        CommunityPost post = communityPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        communityPostRepository.delete(post);
    }

    // 특정 게시판 타입에 해당하는 게시글 찾기
    public Optional<CommunityPost> getPostByIdAndBoardType(Long id, String boardType) {
        try {
            CommunityPostType type = CommunityPostType.valueOf(boardType.toUpperCase()); // String → Enum 변환
            return communityPostRepository.findByIdAndBoardType(id, type);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid board type: " + boardType);
        }
    }
}
