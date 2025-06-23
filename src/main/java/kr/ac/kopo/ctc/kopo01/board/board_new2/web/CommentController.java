package kr.ac.kopo.ctc.kopo01.board.board_new2.web;

import kr.ac.kopo.ctc.kopo01.board.board_new2.domain.Comment;
import kr.ac.kopo.ctc.kopo01.board.board_new2.domain.User;
import kr.ac.kopo.ctc.kopo01.board.board_new2.repository.CommentRepository;
import kr.ac.kopo.ctc.kopo01.board.board_new2.repository.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
public class CommentController {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentController(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @PostMapping("/comments/create")
    public String createComment(@RequestParam Long postId,
                                @RequestParam(required = false) String title,
                                @RequestParam String content,
                                HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/users/login";
        }

        // title이 비어있으면 기본값 설정
        String commentTitle = (title != null && !title.trim().isEmpty())
                ? title.trim()
                : "댓글";

        Comment comment = Comment.builder()
                .post(postRepository.findById(postId).orElseThrow())
                .userId(currentUser.getId())  // 로그인한 사용자 ID 사용
                .title(commentTitle)
                .content(content)
                .build();

        commentRepository.save(comment);
        return "redirect:/posts/" + postId;
    }

    @GetMapping("/comments/edit/{id}")
    public String editCommentForm(@PathVariable Long id, Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/users/login";
        }

        Comment comment = commentRepository.findById(id).orElseThrow();

        // 권한 확인: 댓글 작성자 또는 ADMIN만 수정 가능
        if (!comment.getUserId().equals(currentUser.getId()) && !"ADMIN".equals(currentUser.getRoles())) {
            throw new IllegalArgumentException("댓글을 수정할 권한이 없습니다.");
        }

        model.addAttribute("comment", comment);
        return "editComment";
    }

    @PostMapping("/comments/edit/{id}")
    public String editComment(@PathVariable Long id,
                              @RequestParam(required = false) String title,
                              @RequestParam String content,
                              HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/users/login";
        }

        Comment comment = commentRepository.findById(id).orElseThrow();

        // 권한 확인
        if (!comment.getUserId().equals(currentUser.getId()) && !"ADMIN".equals(currentUser.getRoles())) {
            throw new IllegalArgumentException("댓글을 수정할 권한이 없습니다.");
        }

        // title이 비어있으면 기본값 설정
        String commentTitle = (title != null && !title.trim().isEmpty())
                ? title.trim()
                : "댓글";

        comment.setTitle(commentTitle);
        comment.setContent(content);
        commentRepository.save(comment);
        return "redirect:/posts/" + comment.getPost().getId();
    }

    @PostMapping("/comments/delete/{id}")
    public String deleteComment(@PathVariable Long id, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/users/login";
        }

        Comment comment = commentRepository.findById(id).orElseThrow();

        // 권한 확인
        if (!comment.getUserId().equals(currentUser.getId()) && !"ADMIN".equals(currentUser.getRoles())) {
            throw new IllegalArgumentException("댓글을 삭제할 권한이 없습니다.");
        }

        Long postId = comment.getPost().getId();
        commentRepository.delete(comment);
        return "redirect:/posts/" + postId;
    }
}