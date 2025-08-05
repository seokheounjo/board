package com.example.mytestproject.dto;
// 이 파일이 속한 패키지(package) 경로를 지정함.
// Java는 패키지를 통해 클래스를 논리적으로 묶고 관리함.

import com.example.mytestproject.entity.Comment;
// 실제 댓글(Comment) 엔티티 클래스에서 데이터를 받아오기 위해 import.
// createCommentDto 메서드 안에서 Comment 객체의 정보를 사용하기 때문에 필요.

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
// Lombok의 기능 중 하나.
// 파라미터가 없는 기본 생성자(즉, new CommentDto() 가능하게 함)를 자동으로 생성해줌.

import lombok.ToString;
// Lombok이 자동으로 toString() 메서드를 만들어 줌.
// 객체를 출력하거나 디버깅할 때 내용을 쉽게 볼 수 있음 (필드 값들을 문자열로 보여줌).

@NoArgsConstructor
// 위에서 설명한 것처럼 기본 생성자(매개변수 없는 생성자)를 자동 생성해줌.
// → 예: new CommentDto(); 가 가능해짐.
// 만약 이게 없으면, 아래에 있는 생성자 때문에 기본 생성자가 자동 생성되지 않음.
@Getter

@ToString
// CommentDto 객체를 출력할 때 필드 값들이 자동으로 문자열로 표현됨.
// 예: toString() 호출 시 → "CommentDto(id=1, articleId=2, nickname=홍길동, body=댓글 내용)"

public class CommentDto {
// DTO(Data Transfer Object): 데이터를 계층 간(Controller ↔ Service ↔ Repository) 주고받기 위한 단순한 객체
// Entity와 분리하여 API나 View에 필요한 데이터만 담을 수 있음 (보안·유지보수 유리)

    private Long id;
    // 댓글의 고유 ID (PK). 실제 DB 테이블의 기본 키 값과 연결됨.

@JsonProperty("article_id")// 자동으로 id값과 매칭시켜준다.
    private Long articleId;
    // 이 댓글이 속한 게시글의 ID (즉, 외래키 FK 개념).
    // 직접 Comment 객체에 연결된 Article 객체의 id 값을 추출해서 저장함.

    private String nickname;
    // 댓글 작성자의 닉네임 (예: "홍길동")

    private String body;
    // 댓글의 본문 내용 (예: "좋은 글 감사합니다.")

    public CommentDto(Long id, Long articleId, String nickname, String body) {
        // 모든 필드를 초기화할 수 있는 생성자.
        // new CommentDto(1L, 2L, "닉네임", "댓글 내용")처럼 사용 가능.

        this.id = id;                   // 필드에 전달된 id 값 저장
        this.articleId = articleId;     // 필드에 게시글 id 저장
        this.nickname = nickname;       // 필드에 닉네임 저장
        this.body = body;               // 필드에 댓글 내용 저장
    }

    public static CommentDto createCommentDto(Comment c) {
        // Entity(Comment)를 받아서 DTO(CommentDto)로 변환하는 정적(static) 메서드
        // 외부에서 CommentDto.createCommentDto(commentEntity) 형태로 사용함

        return new CommentDto(
                c.getId(),                // Comment 엔티티에서 댓글 ID 추출
                c.getArticle().getId(),   // 연결된 Article 엔티티에서 게시글 ID 추출
                c.getNickname(),          // 댓글 작성자 닉네임
                c.getBody()               // 댓글 본문 내용
        );
        // 위 4개의 값을 받아 새로운 CommentDto 객체를 생성해서 반환
    }
}
