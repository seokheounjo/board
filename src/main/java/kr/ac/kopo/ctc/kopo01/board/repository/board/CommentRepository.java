package kr.ac.kopo.ctc.kopo01.board.repository.board;

import kr.ac.kopo.ctc.kopo01.board.domain.board.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
