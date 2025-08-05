package com.example.mytestproject.service;

import com.example.mytestproject.dto.ArticleForm;
import com.example.mytestproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.fasterxml.jackson.databind.cfg.CoercionInputShape.Array;
import static org.junit.jupiter.api.Assertions.*; //사용할 클래스 패키지 예비로 임포트

@SpringBootTest // main에 있는 레포지터리와 DB를 사용하기위함
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Test
    void findAll() {
        //예상되는 데이터 작성 (많다면 몇개만 가져온다)
        Article first = new Article(1L, "111", "111");
        Article second = new Article(2L, "222", "222");
        Article third = new Article(3L, "333", "333");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(first, second, third));

        // 실제 db 데이터 수집
        List<Article> articleList = articleService.findAll();

        //1==2 비교
        assertEquals(expected.toString(), articleList.toString());
    }

    @Test
    void articles_success() {
        // 예상 데이터
        Long id = 1L;
        Article expected = new Article(id, "111","111");
        //실제 데이터
        Article article = articleService.articles(id);
        //비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void articles_fail() {
        // 예상 데이터
        Long id = -1L;
        Article expected = null;

        //실제 데이터
        Article article = articleService.articles((id));

        //비교
        assertEquals(expected, article);
    }

    @Test
    void create_suc() {
        //예상 데이터
        String title = "444";
        String content = "444";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(4L, title, content);
        //실제 데이터
        Article article = articleService.create(dto);
        //비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void create_fail__dto에_id가_존재하는_실패테스트() {
        //예상 데이터
        Long id = 4L;
        String title = "444";
        String content = "444";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = null;
        //실제 데이터

        Article article = articleService.create(dto);
        //비교
        assertEquals(expected, article);
    }
}