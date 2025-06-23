package kr.ac.kopo.ctc.kopo01.board.board_new2.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String content;

    private Long userId;
    private Long boardId;

    // 조회수 추가
    @Builder.Default
    private Long viewCount = 0L;

    // 좋아요수 추가
    @Builder.Default
    private Long likeCount = 0L;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // 조회수 증가 메서드 (null 안전 처리)
    public void incrementViewCount() {
        if (this.viewCount == null) {
            this.viewCount = 0L;
        }
        this.viewCount++;
    }

    // 좋아요수 증가 메서드 (null 안전 처리)
    public void incrementLikeCount() {
        if (this.likeCount == null) {
            this.likeCount = 0L;
        }
        this.likeCount++;
    }

    // 좋아요수 감소 메서드 (null 안전 처리)
    public void decrementLikeCount() {
        if (this.likeCount == null) {
            this.likeCount = 0L;
        } else if (this.likeCount > 0) {
            this.likeCount--;
        }
    }

    // Getter 메서드들 (null 안전 처리)
    public Long getViewCount() {
        return this.viewCount != null ? this.viewCount : 0L;
    }

    public Long getLikeCount() {
        return this.likeCount != null ? this.likeCount : 0L;
    }
}