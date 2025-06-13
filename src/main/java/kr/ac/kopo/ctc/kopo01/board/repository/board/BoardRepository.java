package kr.ac.kopo.ctc.kopo01.board.repository.board;

import kr.ac.kopo.ctc.kopo01.board.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
