package kr.ac.kopo.ctc.kopo01.board.repository.board;


import kr.ac.kopo.ctc.kopo01.board.domain.board.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
