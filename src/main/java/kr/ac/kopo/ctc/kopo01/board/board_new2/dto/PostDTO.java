// PostDTO.java - 게시글 데이터를 뷰 계층에 전달하기 위한 Data Transfer Object 정의
package kr.ac.kopo.ctc.kopo01.board.board_new2.dto;

import kr.ac.kopo.ctc.kopo01.board.board_new2.domain.Post;
import kr.ac.kopo.ctc.kopo01.board.board_new2.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data                     // getter, setter, equals, hashCode, toString 자동 생성
@Builder                  // 빌더 패턴 자동 생성
@NoArgsConstructor        // 기본 생성자 자동 생성
@AllArgsConstructor       // 모든 필드를 인자로 받는 생성자 자동 생성
public class PostDTO {

    // 게시글 고유 ID
    private Long id;

    // 게시글 제목
    private String title;

    // 게시글 본문 내용
    private String content;

    // 작성자 ID (User 엔티티의 id)
    private Long userId;

    // 작성자 이름 (username)
    private String username;

    // 게시판 이름 (확장성을 고려한 필드, 현재는 사용하지 않을 수도 있음)
    private String boardName;

    // 게시판 ID (Post가 속한 게시판의 고유 ID)
    private Long boardId;

    // 조회 수
    private Long viewCount;

    // 좋아요 수
    private Long likeCount;

    // 작성일시
    private LocalDateTime createdAt;

    // 수정일시
    private LocalDateTime updatedAt;

    // 작성일시를 "yyyy-MM-dd HH:mm" 포맷으로 문자열로 변환
    public String getFormattedCreatedAt() {
        if (createdAt == null) return "";  // 널이면 빈 문자열 반환
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    // 수정일시를 "yyyy-MM-dd HH:mm" 포맷으로 문자열로 변환
    public String getFormattedUpdatedAt() {
        if (updatedAt == null) return "";
        return updatedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    // Post 엔티티와 User 엔티티를 입력받아 PostDTO 객체로 변환하는 정적 팩토리 메서드
    public static PostDTO from(Post post, User user) {
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle() != null ? post.getTitle() : "제목 없음")          // 제목이 null이면 기본값
                .content(post.getContent() != null ? post.getContent() : "내용 없음")    // 내용이 null이면 기본값
                .userId(post.getUserId())
                .username(user != null ? user.getUsername() : "알 수 없음")              // user 객체가 null이면 기본값
                .boardId(post.getBoardId())
                .viewCount(post.getViewCount() != null ? post.getViewCount() : 0L)       // null 방지
                .likeCount(post.getLikeCount() != null ? post.getLikeCount() : 0L)       // null 방지
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
