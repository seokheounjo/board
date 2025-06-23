package kr.ac.kopo.ctc.kopo01.board.board_new2.repository;

import kr.ac.kopo.ctc.kopo01.board.board_new2.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long id);
}
