package springbootApplication.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "community_board")
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
    private User user; // user_id는 User 엔티티와 연관되어야 하므로 User 타입으로 수정

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "title", nullable = false, columnDefinition = "TEXT")
    private String title;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // LocalDateTime을 사용하여 시간 저장

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now(); // LocalDateTime을 사용하여 시간 저장

    @Column(name = "board_type", nullable = false)
    private String boardType; // boardType 필드 추가

    // 기본 생성자는 Lombok의 @NoArgsConstructor 애너테이션으로 자동 생성됨

    // Builder 패턴을 사용하려면 생성자를 명시적으로 추가
    @Builder
    public CommunityPost(User user, String content, String title) {
        this.user = user;
        this.content = content;
        this.title = title;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}


