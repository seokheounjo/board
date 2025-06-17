// 패키지 선언부: 이 클래스가 속해 있는 패키지를 정의한다.
// 패키지 구조는 보통 회사 도메인을 거꾸로 한 후 프로젝트와 모듈 이름 순으로 구성된다.
package kr.ac.kopo.ctc.kopo01.board.web;

// Spring Framework의 Controller 역할을 하는 클래스임을 알리는 애너테이션을 사용하기 위해 import
import org.springframework.stereotype.Controller;

// 데이터 전달을 위해 사용하는 모델(Model) 객체를 위한 import
import org.springframework.ui.Model;

// HTTP GET 요청을 처리할 메서드임을 명시하는 애너테이션을 사용하기 위해 import
import org.springframework.web.bind.annotation.GetMapping;

// 이 클래스는 Spring MVC에서 요청을 처리하고 응답을 리턴하는 컨트롤러 역할을 한다.
@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";  // templates/index.html 또는 index.jsp
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("name", "kopo00");  // 모델에 값 삽입
        return "home";  // templates/home.html 또는 home.jsp
    }

    @GetMapping("/login")
    public String login() {
        return "login";  // 로그인 화면 뷰 반환
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";  // 관리자 페이지 뷰 반환
    }
}
