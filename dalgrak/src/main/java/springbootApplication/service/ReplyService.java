package springbootApplication.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springbootApplication.domain.Reply;
import springbootApplication.repository.ReplyRepository;
import springbootApplication.repository.UserRepository;

import java.util.List;

@Service
public class ReplyService {
    
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;

    public ReplyService(ReplyRepository replyRepository, UserRepository userRepository) {
        this.replyRepository = replyRepository;
        this.userRepository = userRepository;
    }

    // 특정 댓글의 답글 가져오기
    public List<Reply> getRepliesByCommentId(Long commentId) {
        return replyRepository.findByParentCommentId(commentId);
    }

    // 답글 삭제 (본인만 가능)
    @Transactional
    public void deleteReply(Long replyId, Long userId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new RuntimeException("Reply not found"));

        if (!reply.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("You can only delete your own reply!");
        }

        replyRepository.delete(reply);
    }
}
