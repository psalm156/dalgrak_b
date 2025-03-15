package springbootApplication.service;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import springbootApplication.domain.Comment;
import springbootApplication.repository.CommentRepository;
import springbootApplication.repository.UserRepository;

import java.util.List;

@Service

public class CommentService {
    
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    // 특정 게시글의 댓글 가져오기
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByCommunityPostId(postId);
    }

    // 댓글 삭제 (본인만 가능하도록)
    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("You can only delete your own comment!");
        }

        commentRepository.delete(comment);
    }
}
