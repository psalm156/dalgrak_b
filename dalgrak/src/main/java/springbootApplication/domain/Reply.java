package springbootApplication.domain;

<<<<<<< HEAD
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

=======
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
>>>>>>> 21cbf23b6f142e5adf336968eb4f4d307e74f9f8
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

<<<<<<< HEAD
    @Column(nullable = false, length = 500)
    private String content;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)  // 부모 댓글
    private Comment parentComment;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)  // 답글 작성자
    private User user;
=======
    private Long commentId; 
    private Long userId; 
    private String content; 

    public Reply() {}

    
    public Reply(Long commentId, Long userId, String content) {
        this.commentId = commentId;
        this.userId = userId;
        this.content = content;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
>>>>>>> 21cbf23b6f142e5adf336968eb4f4d307e74f9f8
}
