package com.example.mytestproject.dto;

import com.example.mytestproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@AllArgsConstructor
@ToString
@Getter
@Setter
public class ArticleForm {
    private Long id;
    private String title;  //제목
    private String content; //내용

    //DTO를 Entity로 반환
    public Article toEntity() {
        return new Article( id,title,content);
    }
}
