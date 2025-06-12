package kr.ac.kopo.ctc.kopo01.board.repository;

import kr.ac.kopo.ctc.kopo01.board.domain.Child;
import kr.ac.kopo.ctc.kopo01.board.domain.Parent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Parent와 Child 엔티티 간의 관계를 테스트하는 클래스
 */
@SpringBootTest  // 스프링 부트 테스트 환경을 설정하는 어노테이션
public class ParentChildTest {

    @Autowired  // 스프링이 관리하는 빈을 자동으로 주입
    private ParentRepository parentRepository;

    @Autowired  // 스프링이 관리하는 빈을 자동으로 주입
    private ChildRepository childRepository;

    /**
     * 각 테스트 메서드 실행 전에 실행되는 초기화 메서드
     * Parent와 Child 데이터를 생성하고 저장하는 역할
     */
    @BeforeEach  // 각 테스트 메서드 실행 전에 실행됨
    public void beforeEach() {
        // 5개의 Parent-Child 쌍을 생성
        for (int i = 1; i <= 5; i++) {
            // Parent 엔티티 생성 및 저장
            Parent parent = new Parent();
            parent.setName("parent" + i);
            parentRepository.save(parent);

            // Child 엔티티 생성 및 Parent와 연관관계 설정 후 저장
            Child child = new Child();
            child.setName("child" + i);
            child.setParent(parent);  // Parent-Child 관계 설정
            childRepository.save(child);
        }
    }

    /**
     * Parent-Child 관계가 정상적으로 설정되었는지 테스트하는 메서드
     */
    @Test  // JUnit 테스트 메서드임을 나타냄
    public void testRelation() {
        System.out.println("데이터 저장 후 관계 테스트 완료");
        // 여기에 실제 검증 로직을 추가할 수 있음
        // 예: assert 구문을 사용한 데이터 검증
    }

    @Test
    public void findAll() {
        List<Child> children = childRepository.findAll();
        for(Child c : children) {
            System.out.println(c.getName());
        }
    }

}