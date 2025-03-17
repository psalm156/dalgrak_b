package springbootApplication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import springbootApplication.domain.Comment;
import springbootApplication.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId, @RequestParam Long userId) {
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.ok("Comment deleted successfully");
    }
}
