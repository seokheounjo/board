package kr.ac.kopo.ctc.kopo01.board.repository.board;

import kr.ac.kopo.ctc.kopo01.board.domain.board.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}