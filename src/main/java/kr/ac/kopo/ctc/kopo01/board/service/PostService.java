package kr.ac.kopo.ctc.kopo01.board.service;

import kr.ac.kopo.ctc.kopo01.board.domain.board.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Post create(Post post);
    Optional<Post> read(Long id);
    List<Post> readAll();
    Post update(Long id, Post post);
    void delete(Long id);
}
