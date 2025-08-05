package com.example.mytestproject.service;

import com.example.mytestproject.api.RestApiController;
import com.example.mytestproject.dto.ArticleForm;
import com.example.mytestproject.entity.Article;
import com.example.mytestproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//@Slf4j
@Service
public class ArticleService {  // service
    private static final Logger log = LogManager.getLogger(RestApiController.class);
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> findAll() {
        return articleRepository.findAll();
    }


    public Article articles(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article entity = dto.toEntity();

        if(entity.getId() != null){
            return null;
        }
        return articleRepository.save(entity);
    }

    public Article patch(Long id, ArticleForm dto) {
        Article article = dto.toEntity();
        log.info("id : {}, article : {}", id, article.toString());

        //db에 해당 인덱스 게시글 존재 확인, 데이터 조회
        Article dbData = articleRepository.findById(id).orElse(null);

        if(dbData == null || id != article.getId()) {
            log.info("잘못된 요청, id : {}, article : {}", id, article.toString());
            return null;
        }
        dbData.patch(article);

        // 만약 id가 잘못되었을 경우 처리
        Article saveData = articleRepository.save(dbData);
        return saveData;
    }


    public Article delete(Long id) {
        Article dbData = articleRepository.findById(id).orElse(null);

        if(dbData == null){
            return null;
        }

        articleRepository.delete(dbData);
        return dbData;
    }
    //에러가 발생했을때 자동으로 롤백을 해주는 함수로 입력이 안된다.
    @Transactional

    public List<Article> createTransation(List<ArticleForm> dtos) {
//        //dtos 를 엔티티 묶음으로 변환
//        List<Article> articles = new ArrayList<>();
//        for(int i = 0; i < dtos.size(); i++){
//            ArticleForm dto = dtos.get(i);
//            Article entity = dto.toEntity();
//            articles.add(entity);
//        }

        List<Article> articleStream = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());

        // DB 저장
        articleStream.stream()
                .forEach(article -> articleRepository.save(article));
        // 강제로 에러 발생
        //findById 로 -1인 데이터 찾기 -> 당연히 없지
        // => orElseTrhrow() - 에러를 발생(ILLEGALaRGUMENTeXCEPTION)
        articleRepository.findById(-1L)
                .orElseThrow(() -> new IllegalArgumentException("송금실패"));

        // 결과 리턴
        return articleStream;
    }
}
