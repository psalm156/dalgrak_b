package springbootApplication.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import springbootApplication.domain.Reply;
import springbootApplication.repository.ReplyRepository;
import springbootApplication.repository.UserRepository;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReplyService {
    
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;
    private final WebPushService webPushService;

    // 특정 댓글의 답글 가져오기
    public List<Reply> getRepliesByCommentId(Long commentId) {
        return replyRepository.findByParentCommentId(commentId);
    }

    // 답글 삭제 (본인만 가능)
    @Transactional
    public void deleteReply(Long replyId, Long userId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new RuntimeException("Reply not found"));

        if (!reply.getUserId().equals(userId)) {
            throw new RuntimeException("You can only delete your own reply!");
        }

        replyRepository.delete(reply);
    }

    @Transactional
    public Reply addReply(Long commentId, Long userId, String content) {
        Reply reply = new Reply(commentId, userId, content);
        return replyRepository.save(reply);

    }
}
