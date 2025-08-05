package com.example.mytestproject.repository;

import com.example.mytestproject.entity.Article;
import com.example.mytestproject.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //ㅁ
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;

    @Test
    @DisplayName("특정_아이디_조회하여_테스트")
    void findByArticleId() {
        //6번 게시글 조회하는 댓글 조회
        Long articleId = 1L;

        //1. expected - 예상 데이터 작성
        Article article = new Article(1L, "111?", "111");
//        Comment fir = new Comment(7L, article, "GLOCK", "업무, 제품, 고객");
//        Comment sec = new Comment(8L, article, "M4", "자원인프라");
//        Comment thr = new Comment(9L, article, "AK47", "규제와 감사");
        List<Comment> expected = Arrays.asList();

        //2.실제 데이터 수집

        List<Comment> commentList = commentRepository.findByArticleId(articleId);

        //3. 비교
        assertEquals(expected.toString(), commentList.toString());
    }

    @Test
    @DisplayName("특정_닉네임으로_댓글_조회")
    void findByNickname() {

        String nickname = "M4";

        // 예상 데이터

        Comment fir = new Comment(2L,
                new Article(4L, "나의 최애는?", "ㅁㄴㅇㄹㄷ"),
                nickname, "인생");
        Comment sec = new Comment(8L,
                new Article(6L, "나의 장점은?", "대댓ㄱㄱㄱ"),
                nickname, "자원인프라");

        // 실제 데이터
        List<Comment> commentList = commentRepository.findByNickname(nickname);
    }
}