package kr.ac.kopo.ctc.kopo01.board.board_new2.web;

import kr.ac.kopo.ctc.kopo01.board.board_new2.domain.Board;
import kr.ac.kopo.ctc.kopo01.board.board_new2.domain.User;
import kr.ac.kopo.ctc.kopo01.board.board_new2.repository.BoardRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/boards")
public class BoardController {

    private final BoardRepository boardRepository;

    public BoardController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 게시판 목록
    @GetMapping
    public String list(Model model) {
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards", boards);
        return "boardList";
    }

    // 게시판 상세
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시판을 찾을 수 없습니다: " + id));
        model.addAttribute("board", board);
        return "boardDetail";
    }

    // 게시판 생성 폼
    @GetMapping("/new")
    public String createForm(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/users/login";
        }

        model.addAttribute("board", new Board());
        return "boardForm";
    }

    // 게시판 생성
    @PostMapping("/new")
    public String create(@ModelAttribute Board board, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/users/login";
        }

        board.setUserId(currentUser.getId());
        boardRepository.save(board);
        return "redirect:/boards";
    }

    // 게시판 수정 폼
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/users/login";
        }

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시판을 찾을 수 없습니다: " + id));

        // 권한 확인: 게시판 생성자만 수정 가능
        if (!board.getUserId().equals(currentUser.getId()) && !"ADMIN".equals(currentUser.getRoles())) {
            throw new IllegalArgumentException("게시판을 수정할 권한이 없습니다.");
        }

        model.addAttribute("board", board);
        return "boardForm";
    }

    // 게시판 수정
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute Board boardData, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/users/login";
        }

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시판을 찾을 수 없습니다: " + id));

        // 권한 확인
        if (!board.getUserId().equals(currentUser.getId()) && !"ADMIN".equals(currentUser.getRoles())) {
            throw new IllegalArgumentException("게시판을 수정할 권한이 없습니다.");
        }

        board.setPostname(boardData.getPostname());
        boardRepository.save(board);
        return "redirect:/boards/" + id;
    }

    // 게시판 삭제
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/users/login";
        }

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시판을 찾을 수 없습니다: " + id));

        // 권한 확인
        if (!board.getUserId().equals(currentUser.getId()) && !"ADMIN".equals(currentUser.getRoles())) {
            throw new IllegalArgumentException("게시판을 삭제할 권한이 없습니다.");
        }

        boardRepository.deleteById(id);
        return "redirect:/boards";
    }
}