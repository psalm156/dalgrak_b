package springbootApplication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import springbootApplication.domain.Reply;
import springbootApplication.service.ReplyService;

import java.util.List;

@RestController
@RequestMapping("/replies")
public class ReplyController {

    private final ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<List<Reply>> getRepliesByCommentId(@PathVariable Long commentId) {
        return ResponseEntity.ok(replyService.getRepliesByCommentId(commentId));
    }

    @DeleteMapping("/{replyId}")
    public ResponseEntity<String> deleteReply(@PathVariable Long replyId, @RequestParam Long userId) {
        replyService.deleteReply(replyId, userId);
        return ResponseEntity.ok("Reply deleted successfully");
    }
}
