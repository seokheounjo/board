package kr.ac.kopo.ctc.kopo01.board.board_new2.web;

import kr.ac.kopo.ctc.kopo01.board.board_new2.domain.Like;
import kr.ac.kopo.ctc.kopo01.board.board_new2.domain.Post;
import kr.ac.kopo.ctc.kopo01.board.board_new2.domain.User;
import kr.ac.kopo.ctc.kopo01.board.board_new2.repository.LikeRepository;
import kr.ac.kopo.ctc.kopo01.board.board_new2.repository.PostRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/likes")
public class LikeController {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    public LikeController(LikeRepository likeRepository, PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
    }

    // 좋아요 토글 (AJAX 요청 처리)
    @PostMapping("/toggle/{postId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> toggleLike(@PathVariable Long postId, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        Map<String, Object> response = new HashMap<>();

        if (currentUser == null) {
            response.put("success", false);
            response.put("message", "로그인이 필요합니다.");
            return ResponseEntity.ok(response);
        }

        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            response.put("success", false);
            response.put("message", "게시글을 찾을 수 없습니다.");
            return ResponseEntity.ok(response);
        }

        // 좋아요 상태 확인
        Optional<Like> existingLike = likeRepository.findByUserIdAndPostId(currentUser.getId(), postId);

        if (existingLike.isPresent()) {
            // 이미 좋아요가 있으면 삭제 (좋아요 취소)
            likeRepository.delete(existingLike.get());
            post.decrementLikeCount();
            postRepository.save(post);

            response.put("success", true);
            response.put("liked", false);
            response.put("likeCount", post.getLikeCount());
            response.put("message", "좋아요를 취소했습니다.");
        } else {
            // 좋아요가 없으면 추가
            Like like = Like.builder()
                    .userId(currentUser.getId())
                    .post(post)
                    .build();
            likeRepository.save(like);

            post.incrementLikeCount();
            postRepository.save(post);

            response.put("success", true);
            response.put("liked", true);
            response.put("likeCount", post.getLikeCount());
            response.put("message", "좋아요를 눌렀습니다.");
        }

        return ResponseEntity.ok(response);
    }

    // 특정 게시글의 좋아요 상태 확인 (AJAX)
    @GetMapping("/status/{postId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getLikeStatus(@PathVariable Long postId, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        Map<String, Object> response = new HashMap<>();

        if (currentUser == null) {
            response.put("liked", false);
            response.put("likeCount", 0);
            return ResponseEntity.ok(response);
        }

        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            response.put("liked", false);
            response.put("likeCount", 0);
            return ResponseEntity.ok(response);
        }

        boolean liked = likeRepository.existsByUserIdAndPostId(currentUser.getId(), postId);

        response.put("liked", liked);
        response.put("likeCount", post.getLikeCount());

        return ResponseEntity.ok(response);
    }
}