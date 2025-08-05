package com.example.mytestproject.ArticleController;


import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.mytestproject.dto.ArticleForm;
import com.example.mytestproject.dto.CommentDto;
import com.example.mytestproject.entity.Article;
import com.example.mytestproject.entity.Comment;
import com.example.mytestproject.repository.ArticleRepository;
import com.example.mytestproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
//@Slf4j //Simple Logging Facade for java
public class ArticleController {
    private static final Logger log = LogManager.getLogger(ArticleController.class);
    //의존성 주입 = DI
    @Autowired  // 이미 생성한 repo 객체를 DI
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;

    @GetMapping("/articles/new")
    public String newArticleForm(){
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
//        System.out.println(form.toString());
        log.info(form.toString());

        // 1.DTO를 엔티티로 변환
        Article article = form.toEntity();
//        System.out.println(article.toString());
        log.info(article.toString());

        // 2.리파지터리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article);
//        System.out.println(saved.toString());
        log.info(saved.toString());


        return "redirect:/articles/" + saved.getId();
//        return "/articles/index";
    }

    @GetMapping("/articles/{id}")
    public String articleId(@PathVariable Long id,Model model){
        //URL로 전달된 변수값을 파라미터로 받겠다는 애너테이션
        log.info("id = "+ id);

        // 1번 - id DB 조회
        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentDtos = commentService.comments(id);

        // 2번 - 모델에 데이터를 등록
        model.addAttribute("data",articleEntity);
        model.addAttribute("commentDtos", commentDtos);

        // 3번 - 사용자에게 화면 전달
        return "articles/articleId";
    }

    @GetMapping("/articles/index")
    public String index(Model model){

        // 1번 - DB에 모든 게시글 데이터 가져오기
        // 업캐스팅
//        Iterable<Article> articleList = articleRepository.findAll(); //리턴형 ? -> 엔티티
        // 다운캐스팅
//        List<Article> articleList2 = (List<Article>)articleRepository.findAll();
        List<Article> articleList = articleRepository.findAll();

        // 2번 - 등록하기
        model.addAttribute("articleList", articleList);
        // 3번 - 화면 리턴
        return "articles/index";
    }
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        //수정할 데이터 가져오기
        Article article = articleRepository.findById(id).orElse(null);
        //등록하기
        model.addAttribute("data", article);
        //뷰 페이지 설정
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        log.info(form.toString());

//        // 1.DTO를 엔티티로 변환
//        Article article = form.toEntity();
//        log.info(article.toString());
//
//        // 2.리파지터리로 엔티티를 DB에 저장
//        Article saved = articleRepository.save(article);
//        log.info(saved.toString());
//
//        return "redirect:/articles/" + saved.getId();
        //교수님 버전
        // 1.DTO를 엔티티로 변환
        Article entity = form.toEntity();
        log.info(entity);
        // 2-1 - 수정하기 전 데이터 불러오기
        //DB에서 데이터찾고
        Article dbData = articleRepository.findById(form.getId()).orElse(null);
        log.info(dbData);
        // 2-2
        //갱신 데이터를 저장하고
        //데이터가 없는 경우처리 (있으면 저장)
        if(dbData != null){
            articleRepository.save(entity);
        }
        // 3번 수정 결과 화면 전환
        return "redirect:/articles/" + entity.getId();
    }

    //HTTP 메서드 = GET, POST, UPDATE, DELETE
    //HTML 메서드 = GET, POST

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제 요청 확인");

        Article article = articleRepository.findById(id).orElse(null);
        log.info(article.toString());

        if(article != null){
            articleRepository.delete(article);
            rttr.addFlashAttribute("msg","삭제가 완료되었습니다.");
        }

        return"redirect:/articles/index";
    }



}
