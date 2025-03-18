package springbootApplication.repository;

import springbootApplication.domain.Comment;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 postId에 달린 댓글을 조회
    List<Comment> findByCommunityPostId(Long postId);

    // 특정 commentId로 댓글 조회
    Optional<Comment> findById(Long id);

}