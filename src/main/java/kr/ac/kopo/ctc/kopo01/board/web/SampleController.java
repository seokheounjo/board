// 패키지 정의: board 웹 컨트롤러 계층에 속하는 클래스임을 명시
package kr.ac.kopo.ctc.kopo01.board.web;

// 도메인 객체와 리포지토리 인터페이스 import
import kr.ac.kopo.ctc.kopo01.board.domain.Sample; // DB 테이블과 매핑되는 Sample 엔티티 클래스
import kr.ac.kopo.ctc.kopo01.board.repository.SampleRepository; // Sample 데이터를 다루는 JPA 리포지토리

// Spring Framework 관련 기능 import
import kr.ac.kopo.ctc.kopo01.board.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired; // 의존성 주입을 위한 애너테이션
import org.springframework.stereotype.Controller; // 이 클래스가 웹 요청을 처리하는 컨트롤러임을 명시
import org.springframework.ui.Model; // 뷰 템플릿에 데이터를 전달하기 위한 모델 객체 (현재는 미사용)
import org.springframework.web.bind.annotation.GetMapping; // GET 요청 매핑을 위한 애너테이션
import org.springframework.web.bind.annotation.RequestMapping; // 클래스 단위의 URL 경로 지정
import org.springframework.web.bind.annotation.ResponseBody; // 응답 결과를 JSON 또는 문자열 그대로 반환

import java.util.List; // Sample 객체 리스트 타입을 사용하기 위한 import

/**
 * SampleController는 /sample 경로 이하의 요청을 처리하는 웹 컨트롤러로,
 * SampleRepository를 통해 DB에서 데이터를 조회하고 JSON 형식으로 반환한다.
 */
@Controller // Spring MVC가 이 클래스를 컨트롤러로 인식하게 함
@RequestMapping("/sample") // 기본 경로를 /sample로 설정
public class SampleController {

    @Autowired // SampleRepository를 자동으로 주입받음 (Spring Bean으로 등록되어 있어야 함)
    private SampleRepository sampleRepository;
    @Autowired
    SampleService sampleService;

    /**
     * /sample/list GET 요청을 처리하는 메서드
     * DB에서 모든 Sample 데이터를 조회한 후, JSON 형식으로 반환한다.
     *
     * @param model Model 객체 (현재 미사용, 뷰에서 데이터를 넘길 때 사용됨)
     * @return Sample 객체 리스트를 JSON 형태로 응답
     */
    @GetMapping("/list") // /sample/list 경로에 대한 GET 요청 처리
    @ResponseBody // 반환되는 객체를 뷰 이름이 아닌 HTTP 응답 본문으로 직렬화하여 전송 (주로 JSON)
    public List<Sample> list(Model model) {
        return sampleRepository.findAll(); // Sample 테이블의 모든 데이터를 조회하여 반환
    }

    @GetMapping("/noTransactional")
    @ResponseBody
    public String noTransactional(Model model){
        sampleService.testNoTransactional();
        return "noTransactional";
    }
    @GetMapping("/noTransactional")
    @ResponseBody
    public String Transactional(Model model){
        sampleService.testNoTransactional();
        return "Transactional";
    }

}
