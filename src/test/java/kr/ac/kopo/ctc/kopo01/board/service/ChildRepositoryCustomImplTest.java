package kr.ac.kopo.ctc.kopo01.board.service;

import kr.ac.kopo.ctc.kopo01.board.domain.Child;
import kr.ac.kopo.ctc.kopo01.board.repository.ChildRepositoryCustom;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChildRepositoryCustomImplTest {

    @Autowired
    @Qualifier("childRepositoryCustomImpl")  // 정확한 Bean 이름을 지정
    private ChildRepositoryCustom childRepositoryCustom;


    @Test
    void selectAll_shouldReturnChildrenWithParentFetched() {
        // when
        List<Child> result = childRepositoryCustom.selectAll();

        // then
        assertNotNull(result);  // 결과 리스트가 null이 아님
        assertFalse(result.isEmpty());  // 최소한 1개 이상 있다고 가정 (없으면 실패)
        assertNotNull(result.get(0).getParent());  // fetchJoin으로 부모가 즉시 로딩되어야 함

        // 디버깅용 출력
        result.forEach(child -> System.out.println("Child: " + child.getName() +
                ", Parent: " + child.getParent().getName()));
    }
}
