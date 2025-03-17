package springbootApplication.repository;

import springbootApplication.domain.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	    List<Comment> findByCommunityPostId(Long postId);
}
=======
import org.springframework.data.jpa.repository.JpaRepository;
import springbootApplication.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostId(Long postId);

    Optional<Comment> findById(Long id);

}

