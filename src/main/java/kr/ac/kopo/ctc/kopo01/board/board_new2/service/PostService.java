package kr.ac.kopo.ctc.kopo01.board.board_new2.service;


import kr.ac.kopo.ctc.kopo01.board.board_new2.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Post create(Post post);
    Optional<Post> read(Long id);
    List<Post> readAll();
    Post update(Long id, Post post);
    void delete(Long id);
}
