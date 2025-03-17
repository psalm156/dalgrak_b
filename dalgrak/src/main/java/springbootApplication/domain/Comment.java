package springbootApplication.domain;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Comment {
    
=======
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comment {

>>>>>>> 21cbf23b6f142e5adf336968eb4f4d307e74f9f8
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

<<<<<<< HEAD
    @Column(nullable = false, length = 500)
    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private CommunityPost communityPost;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)  // 댓글 작성자
    private User user;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replies = new ArrayList<>();

}

=======
    private Long postId;
    private Long userId;
    private String content;

    public Comment(Long postId, Long userId, String content) {
        this.postId = postId;
        this.userId = userId;
        this.content = content;
    }
}
>>>>>>> 21cbf23b6f142e5adf336968eb4f4d307e74f9f8
