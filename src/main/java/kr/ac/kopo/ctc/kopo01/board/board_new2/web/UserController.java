package kr.ac.kopo.ctc.kopo01.board.board_new2.web;

import kr.ac.kopo.ctc.kopo01.board.board_new2.domain.User;
import kr.ac.kopo.ctc.kopo01.board.board_new2.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 사용자 목록 (관리자만 접근 가능)
    @GetMapping
    public String list(Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || !"ADMIN".equals(currentUser.getRoles())) {
            return "redirect:/users/login";
        }

        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "userList";
    }

    // 사용자 상세 (본인 또는 관리자만 접근 가능)
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/users/login";
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + id));

        // 본인이거나 관리자인 경우에만 접근 허용
        if (!currentUser.getId().equals(id) && !"ADMIN".equals(currentUser.getRoles())) {
            return "redirect:/posts";  // 권한 없으면 메인으로
        }

        model.addAttribute("user", user);
        return "userDetail";
    }

    // 사용자 등록 폼
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("user", new User());
        return "userForm";
    }

    // 사용자 등록
    @PostMapping("/new")
    public String create(@ModelAttribute User user, Model model) {
        // username 중복 체크
        if (userRepository.existsByUsername(user.getUsername())) {
            model.addAttribute("error", "이미 존재하는 아이디입니다.");
            model.addAttribute("user", user);
            return "userForm";
        }

        user.setJoindate(LocalDate.now());

        // 일반사용자는 USER 권한만 설정 가능
        if (user.getRoles() == null || (!user.getRoles().equals("USER") && !user.getRoles().equals("ADMIN"))) {
            user.setRoles("USER");
        }

        try {
            userRepository.save(user);
            return "redirect:/users";
        } catch (Exception e) {
            model.addAttribute("error", "회원가입 중 오류가 발생했습니다.");
            model.addAttribute("user", user);
            return "userForm";
        }
    }

    // 사용자 수정 폼 (본인 또는 관리자만 접근 가능)
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/users/login";
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + id));

        // 본인이거나 관리자인 경우에만 수정 허용
        if (!currentUser.getId().equals(id) && !"ADMIN".equals(currentUser.getRoles())) {
            return "redirect:/posts";
        }

        model.addAttribute("user", user);
        model.addAttribute("isOwner", currentUser.getId().equals(id));
        return "userForm";
    }

    // 사용자 수정 (본인 또는 관리자만 가능)
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute User userData, Model model, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/users/login";
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + id));

        // 본인이거나 관리자인 경우에만 수정 허용
        if (!currentUser.getId().equals(id) && !"ADMIN".equals(currentUser.getRoles())) {
            return "redirect:/posts";
        }

        // username이 변경된 경우 중복 체크 (자기 자신 제외)
        if (!user.getUsername().equals(userData.getUsername())) {
            if (userRepository.existsByUsername(userData.getUsername())) {
                model.addAttribute("error", "이미 존재하는 아이디입니다.");
                model.addAttribute("user", userData);
                model.addAttribute("isOwner", currentUser.getId().equals(id));
                return "userForm";
            }
        }

        try {
            user.setUsername(userData.getUsername());
            user.setPassword(userData.getPassword());
            user.setName(userData.getName());

            // 관리자만 권한 변경 가능, 본인은 기존 권한 유지
            if ("ADMIN".equals(currentUser.getRoles())) {
                user.setRoles(userData.getRoles());
            }

            userRepository.save(user);
            return "redirect:/users/" + id;
        } catch (Exception e) {
            model.addAttribute("error", "사용자 정보 수정 중 오류가 발생했습니다.");
            model.addAttribute("user", userData);
            model.addAttribute("isOwner", currentUser.getId().equals(id));
            return "userForm";
        }
    }

    // 사용자 삭제 (관리자만 가능, 본인은 삭제 불가)
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null || !"ADMIN".equals(currentUser.getRoles())) {
            return "redirect:/users/login";
        }

        // 자기 자신은 삭제할 수 없음
        if (currentUser.getId().equals(id)) {
            return "redirect:/users?error=cannot_delete_self";
        }

        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다: " + id);
        }

        userRepository.deleteById(id);
        return "redirect:/users";
    }

    // 로그인 폼
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        try {
            User user = userRepository.findByUsername(username).orElse(null);

            if (user != null && user.getPassword().equals(password)) {
                // 로그인 성공
                session.setAttribute("currentUser", user);
                return "redirect:/posts";
            } else {
                // 로그인 실패
                model.addAttribute("error", "아이디 또는 비밀번호가 틀렸습니다.");
                return "login";
            }
        } catch (Exception e) {
            // 중복 사용자 등의 예외 처리
            model.addAttribute("error", "로그인 중 오류가 발생했습니다. 관리자에게 문의하세요.");
            return "login";
        }
    }

    // 로그아웃
    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/posts";
    }
}