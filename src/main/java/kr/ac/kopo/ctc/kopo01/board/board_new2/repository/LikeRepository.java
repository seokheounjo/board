package kr.ac.kopo.ctc.kopo01.board.board_new2.repository;

import kr.ac.kopo.ctc.kopo01.board.board_new2.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    // 특정 유저가 특정 게시글에 좋아요를 했는지 확인
    Optional<Like> findByUserIdAndPostId(Long userId, Long postId);

    // 특정 게시글의 좋아요 개수 계산
    @Query("SELECT COUNT(l) FROM Like l WHERE l.post.id = :postId")
    Long countByPostId(@Param("postId") Long postId);

    // 특정 유저가 특정 게시글에 좋아요를 했는지 확인 (boolean 반환)
    boolean existsByUserIdAndPostId(Long userId, Long postId);
}