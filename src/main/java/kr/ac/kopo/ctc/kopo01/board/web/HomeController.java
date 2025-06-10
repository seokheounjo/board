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
@Controller // 이 클래스가 Controller 역할을 함을 Spring에게 알려주는 애너테이션
public class HomeController {

    /**
     * 메서드 설명:
     * - 이 메서드는 "/home"이라는 경로로 들어온 GET 요청을 처리한다.
     * - Model 객체를 통해 데이터를 View(템플릿)에 전달한다.
     * - 최종적으로 "home"이라는 이름의 뷰를 반환하여 사용자에게 화면을 보여준다.
     */
    @GetMapping("/home") // 클라이언트가 "/home" 경로로 GET 요청을 보낼 때 이 메서드가 실행됨
    public String home(Model model) { // Model 객체는 컨트롤러에서 뷰로 데이터를 전달할 때 사용
        // "name"이라는 이름의 속성(attribute)을 "kopo01"이라는 값과 함께 모델에 추가
        // 이 값은 뷰 템플릿(예: home.mustache, home.html 등)에서 ${name}처럼 사용 가능
        model.addAttribute("name", "kopo01");

        // "home"이라는 이름의 뷰를 반환함
        // 이는 보통 src/main/resources/templates/home.html 또는 home.mustache 파일을 의미
        return "home";
    }
}
