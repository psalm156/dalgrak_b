package springbootApplication.service;

import springbootApplication.domain.CommunityPost; 
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

    public Optional<CommunityPost> getPostById(Long id) { 
        return communityPostRepository.findById(id);
    }

    public CommunityPost createPost(CommunityPost post) {
        return communityPostRepository.save(post);
    }

    public CommunityPost updatePost(Long id, CommunityPost updatedPost) { 
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

