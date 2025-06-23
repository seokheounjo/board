package kr.ac.kopo.ctc.kopo01.board.board_new2.web;

import kr.ac.kopo.ctc.kopo01.board.board_new2.domain.User;
import kr.ac.kopo.ctc.kopo01.board.board_new2.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 관리자 권한 체크
    private boolean isAdmin(HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        return currentUser != null && "ADMIN".equals(currentUser.getRoles());
    }

    // 데이터 정리 페이지
    @GetMapping("/cleanup")
    public String cleanupPage(Model model, HttpSession session) {
        if (!isAdmin(session)) {
            return "redirect:/users/login";
        }

        // 중복된 username들 찾기
        List<String> duplicateUsernames = userRepository.findDuplicateUsernames();
        model.addAttribute("duplicateUsernames", duplicateUsernames);

        return "adminCleanup";
    }

    // 중복 사용자 정리
    @PostMapping("/cleanup/users")
    public String cleanupDuplicateUsers(HttpSession session, Model model) {
        if (!isAdmin(session)) {
            return "redirect:/users/login";
        }

        try {
            // 중복된 사용자들을 정리하는 로직
            // 실제로는 SQL로 처리하는 것이 더 효율적이지만, 여기서는 Java로 구현
            List<String> duplicateUsernames = userRepository.findDuplicateUsernames();

            for (String username : duplicateUsernames) {
                List<User> duplicateUsers = userRepository.findAll().stream()
                        .filter(user -> username.equals(user.getUsername()))
                        .sorted((a, b) -> Long.compare(a.getId(), b.getId()))
                        .toList();

                // 첫 번째(가장 낮은 ID)를 제외하고 나머지 삭제
                for (int i = 1; i < duplicateUsers.size(); i++) {
                    userRepository.delete(duplicateUsers.get(i));
                }
            }

            model.addAttribute("success", "중복 사용자 정리가 완료되었습니다.");
        } catch (Exception e) {
            model.addAttribute("error", "정리 중 오류가 발생했습니다: " + e.getMessage());
        }

        return "redirect:/admin/cleanup";
    }
}