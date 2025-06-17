package kr.ac.kopo.ctc.kopo01.board.repository.board;

import kr.ac.kopo.ctc.kopo01.board.domain.board.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String admin);
}