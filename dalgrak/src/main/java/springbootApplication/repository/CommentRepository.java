package springbootApplication.repository;

import springbootApplication.domain.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	    List<Comment> findByCommunityPostId(Long postId);
}