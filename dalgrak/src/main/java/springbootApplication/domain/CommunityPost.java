package springbootApplication.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "CommunityPost")
@Getter
@Setter
@NoArgsConstructor
public class CommunityPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", updatable = false)
    private Long post_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; 

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "title", nullable = false, columnDefinition = "TEXT")
    private String title;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(nullable = false)
<<<<<<< HEAD
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "board_type", nullable = false)
    private String boardType; 
=======
    private LocalDateTime updatedAt = LocalDateTime.now(); 
    
    @OneToMany(mappedBy = "communityPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
    
>>>>>>> 7c7b34bd84b35458d4e52b02a4d76aab084129a6

    @Builder
<<<<<<< HEAD
    public CommunityPost(User user, String content, String title) {
        this.user = user;
=======
    public CommunityPost(User author, String content, String title, CommunityPostType postType) {
        this.author = author;
>>>>>>> 7c7b34bd84b35458d4e52b02a4d76aab084129a6
        this.content = content;
        this.title = title;
        this.postType = postType;
    }
}


