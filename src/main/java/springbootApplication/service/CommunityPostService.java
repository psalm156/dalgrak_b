package springbootApplication.service;

import springbootApplication.domain.CommunityPost; // 적절한 import 경로 수정 필요
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

    public Optional<CommunityPost> getPostById(Long id) { // id 타입을 Long으로 수정
        return communityPostRepository.findById(id);
    }

    public CommunityPost createPost(CommunityPost post) {
        return communityPostRepository.save(post);
    }

    public CommunityPost updatePost(Long id, CommunityPost updatedPost) { // id 타입을 Long으로 수정
        return communityPostRepository.findById(id)
                .map(post -> {
                    post.setContent(updatedPost.getContent());
                    return communityPostRepository.save(post);
                }).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    public void deletePost(Long id) {
        communityPostRepository.findById(id).ifPresentOrElse(
            post -> communityPostRepository.delete(post),
            () -> {
                throw new RuntimeException("Post not found");
            }
        );
    }
}

