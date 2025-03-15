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

    public List<CommunityPost> getAllPosts() { //모든 게시물 불러오기
        return communityPostRepository.findAll();
    }


    public List<CommunityPost> getPostsByType(CommunityPostType type) { //게시판 타입 이동
        return communityPostRepository.findByPostType(type);
    }

    public Optional<List<CommunityPost>> getPostsByTitle(String title) { //title로 검색
        return Optional.ofNullable(communityPostRepository.findByTitleContaining(title));
    }
    
    public CommunityPost createPost(CommunityPost post) { //게시물 작성
        return communityPostRepository.save(post);
    }

    public CommunityPost updatePost(Long id, CommunityPost updatedPost, Long userId) { // 게시물 수정
        return communityPostRepository.findById(id)
                .map(post -> {
                    if (!post.getAuthor().getUserId().equals(userId)) {
                        throw new RuntimeException("You can only edit your own posts.");
                    }
                    post.setContent(updatedPost.getContent());
                    return communityPostRepository.save(post);
                }).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public void deletePost(Long id, Long userId) { //게시물 삭제
        communityPostRepository.findById(id)
                .ifPresentOrElse(post -> {
                    if (!post.getAuthor().getUserId().equals(userId)) {
                        throw new RuntimeException("You can only delete your own posts.");
                    }
                    communityPostRepository.delete(post);
                }, () -> {
                    throw new RuntimeException("Post not found");
                });
    }
}

