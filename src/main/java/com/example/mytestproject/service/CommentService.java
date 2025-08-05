package com.example.mytestproject.service;
// 이 클래스가 속한 패키지를 정의함. Spring 프로젝트에서 보통 'service' 폴더 아래에 위치함.
// 서비스(Service) 계층은 비즈니스 로직을 담당하는 계층임.

import com.example.mytestproject.dto.CommentDto;
// 댓글 정보를 외부에 전달할 때 사용할 DTO(Data Transfer Object) 클래스 import

import com.example.mytestproject.entity.Article;
import com.example.mytestproject.entity.Comment;
// 댓글의 실제 DB 테이블과 매핑되는 JPA 엔티티 클래스 import

import com.example.mytestproject.repository.ArticleRepository;
import com.example.mytestproject.repository.CommentRepository;
// 댓글과 게시글 데이터를 DB에서 읽고 쓰기 위해 사용하는 JPA Repository 인터페이스들 import

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// Spring이 이 클래스를 '서비스 컴포넌트'로 인식하게 해주는 어노테이션들 import

import java.util.List;
import java.util.stream.Collectors;


@Service
// 이 클래스가 '비즈니스 로직 처리 클래스'임을 Spring에게 알려줌
// Spring은 이 클래스를 Bean으로 등록해서 의존성 주입 대상이 되게 함

public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    // 댓글 데이터 처리를 담당하는 Repository 인터페이스를 주입받음
    // 스프링이 자동으로 구현체(프록시 객체)를 넣어줌

    @Autowired
    private ArticleRepository articleRepository;
    // 게시글 데이터를 조회하기 위한 Repository도 필요 시 사용 가능 (현재 메서드에서는 사용되지 않지만 향후 확장 가능)

    public List<CommentDto> comments(Long articleId) {
        // 특정 게시글(articleId)에 달린 모든 댓글을 가져오는 메서드
        // Controller에서 호출됨

//        // 1. 댓글 엔티티 조회
//        List<Comment> comments = commentRepository.findByArticleId(articleId);
//        // comment 테이블에서 article_id가 주어진 값과 같은 모든 댓글을 가져옴
//        // 내부적으로 SELECT * FROM comment WHERE article_id = ? 실행됨
//
//        // 2. 댓글 엔티티 → DTO로 변환할 리스트 생성
//        List<CommentDto> dtos = new ArrayList<CommentDto>();
//        // 최종적으로 Controller나 View에 전달할 DTO 목록을 만들기 위한 리스트
//
//        // 3. 변환 반복 처리
////        for(int i = 0; i < comments.size(); i++) {
////            Comment c = comments.get(i);                         // i번째 댓글 엔티티 꺼내기
////            CommentDto dto = CommentDto.createCommentDto(c);     // Comment 엔티티 → DTO로 변환
////            dtos.add(dto);                                        // 변환된 DTO를 결과 리스트에 추가
////        }
//        // 4. 변환된 DTO 리스트 반환
//        return dtos;
//        // Controller 또는 View 단에서 이 리스트를 받아 화면에 출력하거나 JSON 응답으로 반환함
//    }

        return commentRepository.findByArticleId(articleId) // 댓글 목록 조회
                .stream() // 리스트를 스트림 형태로 처리
                .map(comment -> CommentDto.createCommentDto(comment)) // 각 엔티티 → DTO로 변환
                .collect(Collectors.toList()); // 변환된 DTO들을 리스트로 수집해서 반환


    }
@Transactional // 로직 중간에 실패 되었을 경우 롤백 처리
    public CommentDto create(Long articleId, CommentDto dto) {
        //1. 게시글이 존재하는지 체크
    //orElseThrow 은 null이 될수 있는 obj(객체) = optional 객체 - 값이 있으면 반환, 없으면 예외
        Article article = articleRepository.findById(articleId).orElseThrow(() ->
                new IllegalArgumentException("댓글 생성에 실패하였습니다!" +
                "요청한 게시글이 존재하지 않습니다."));
        //2. 댓글 DTO 를 entity로 변환
        Comment comment = Comment.createComment(dto, article);

            //3. DB로 저장
        Comment created = commentRepository.save(comment);

            //4. DTO 변환
        return CommentDto.createCommentDto(created);
    }
@Transactional
    public CommentDto update(Long id, CommentDto dto) {
        // 수정하려는 대상이 DB에 있는지 조회
    Comment dbData = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 수정에 실패 하였습니다." +
            "요청하신 원본 댓글이 존재하지 않습니다."));

    // 원본 데이터를 수정
    dbData.patch(dto);

    // 수정 데이터를 갱싱(DB)
    Comment updateData = commentRepository.save(dbData);

    // 변경된 데이터를 entity -> dto로 변환 후 반환
    return CommentDto.createCommentDto(dbData);

    }

    @Transactional
    public CommentDto delete(Long id) {
        // 수정하려는 대상이 DB에 있는지 조회
        Comment dbData = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패 하였습니다." +
                "요청하신 원본 댓글이 존재하지 않습니다."));

        // 원본 데이터를 삭제

        commentRepository.delete(dbData);

        // 변경된 데이터를 entity -> dto로 변환 후 반환
        return CommentDto.createCommentDto(dbData);

    }

}
