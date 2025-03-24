package springbootApplication.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String content;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)  // 부모 댓글
    private Comment parentComment;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)  // 답글 작성자
    private User user;

    public Reply() {}

    public Reply(Comment parentComment, User user, String content) {
        this.parentComment = parentComment;
        this.user = user;
        this.content = content;
    }
}
