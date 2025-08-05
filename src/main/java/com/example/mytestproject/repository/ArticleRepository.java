package com.example.mytestproject.repository;


import com.example.mytestproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ArticleRepository
        extends CrudRepository<Article, Long> {
    @Override
    ArrayList<Article> findAll();
}
