package com.example.mytestproject.api;

import com.example.mytestproject.dto.ArticleForm;
import com.example.mytestproject.entity.Article;
import com.example.mytestproject.repository.ArticleRepository;
import com.example.mytestproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Slf4j
@RestController
public class RestApiController {

    private static final Logger log = LogManager.getLogger(RestApiController.class);
    @Autowired  // 객체 주입, 객체들을 연결해준다
    private ArticleService articleService;

    @GetMapping("/api/allArticles")
    public List<Article> AllArticles(){
        // 데이터 리턴 -> 어떤? 게시물이 단수? 복수? -> 복수 : List<>
        // 데이터는 어디? : DB 안에
        // DB 접근은 어떻게? : Repository 초기화
        return articleService.findAll();
    }

    @GetMapping("/api/allArticles/{id}")
    public Article articles(@PathVariable Long id){
        return articleService.articles(id);
    }

    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto){
        Article serviceRes = articleService.create(dto);
        //생성이 성공이였을 때 / 생성이 실패했을 때
        //삼항연산자
        return  (serviceRes != null) ?
                ResponseEntity.status(HttpStatus.OK).body(serviceRes) :
                    ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/api/articles/{id}")
        public ResponseEntity<Article> patch(@PathVariable Long id,
                                             @RequestBody ArticleForm dto){
        Article patchData = articleService.patch(id, dto);

        return (patchData != null) ?
                ResponseEntity.status(HttpStatus.OK).body(patchData) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        }

    // 데이터 삭제할 때
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        Article serviceRes = articleService.delete(id);

        return (serviceRes != null) ?
                ResponseEntity.status(HttpStatus.OK).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();


    }
    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(
            @RequestBody List<ArticleForm>dtos
    ){
        List<Article> createList =
                articleService.createTransation(dtos);
        return (createList != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


//
//    @GetMapping("/api/articles/{id}")
//    public Article articles(@PathVariable Long id){
//        return articleRepository.findById(id).orElse(null);
//    }
//
//    @PostMapping("/api/articles")
//    public Article create(@RequestBody ArticleForm dto){
//        Article article = dto.toEntity();
//        return articleRepository.save(article);
//    }
//
//    @PatchMapping("/api/articles/{id}")
//    public ResponseEntity<Article> patch(@PathVariable Long id,
//                         @RequestBody ArticleForm dto){
//
//        // 게시글 ID, 수정할 내용 - 획득 완료
//        // 수정할 dto -> entity
//        // entity로 dto를 변환
//        Article article = dto.toEntity();
//        log.info("id : {}, article : {}", id, article.toString());
//
//        //db에 해당 인덱스 게시글 존재 확인, 데이터 조회
//        Article dbData = articleRepository.findById(id).orElse(null);
//
//        // 요청 검증
//        if(dbData == null || id != article.getId()){
//            log.info("잘못된 요청, id : {}, article : {}", id, article.toString());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//
//        dbData.patch(article);
//
//        // 만약 id가 잘못되었을 경우 처리
//        Article saveData = articleRepository.save(dbData);
//        return ResponseEntity.status(HttpStatus.OK).body(saveData);
//    }
//



}



