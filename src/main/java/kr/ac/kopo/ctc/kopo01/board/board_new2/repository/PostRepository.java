package kr.ac.kopo.ctc.kopo01.board.board_new2.repository;


import kr.ac.kopo.ctc.kopo01.board.board_new2.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTitleContainingOrContentContainingOrUserId(String title, String content, Long userId);

}
