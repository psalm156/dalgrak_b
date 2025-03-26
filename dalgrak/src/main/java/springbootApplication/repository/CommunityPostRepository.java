package springbootApplication.repository;

import springbootApplication.domain.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {
	Optional<CommunityPost> findByIdAndBoardType(Long id, CommunityPostType boardType);
}
