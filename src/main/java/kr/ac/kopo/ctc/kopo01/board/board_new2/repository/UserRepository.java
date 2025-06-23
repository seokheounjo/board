package kr.ac.kopo.ctc.kopo01.board.board_new2.repository;

import kr.ac.kopo.ctc.kopo01.board.board_new2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    // username 중복 체크
    boolean existsByUsername(String username);

    // 중복된 username들 찾기 (관리용)
    @Query("SELECT u.username FROM User u GROUP BY u.username HAVING COUNT(u.username) > 1")
    List<String> findDuplicateUsernames();
}