package kr.ac.kopo.ctc.kopo01.board.board_new2.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "post_likes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"userId", "postId"})  // 같은 유저가 같은 게시글에 중복 좋아요 방지
})
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    private Post post;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}