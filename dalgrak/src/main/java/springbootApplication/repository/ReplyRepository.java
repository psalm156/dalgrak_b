package springbootApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD

=======
>>>>>>> 21cbf23b6f142e5adf336968eb4f4d307e74f9f8
import springbootApplication.domain.Reply;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findByParentCommentId(Long commentId);

    List<Reply> findByCommentId(Long commentId);
    List<Reply> findByUserId(Long userId);

}
