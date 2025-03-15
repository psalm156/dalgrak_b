package springbootApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springbootApplication.domain.Reply;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findByCommentId(Long commentId);
    List<Reply> findByUserId(Long userId);
}
