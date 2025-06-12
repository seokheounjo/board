
package kr.ac.kopo.ctc.kopo01.board.repository;

import kr.ac.kopo.ctc.kopo01.board.domain.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA의 JpaRepository를 상속받는 ChildRepository 인터페이스
 * @author [작성자 이름]
 */
public interface ChildRepository extends JpaRepository<Child, Long> {
//    @Query("SELECT c FROM Child c left join c.parent")
//    List<Child> fetchAll();
    // JpaRepository<엔티티타입, ID타입>
    // - 첫 번째 제네릭 타입 <Child>: 이 리포지토리가 다룰 엔티티 클래스
    // - 두 번째 제네릭 타입 <Long>: 엔티티의 기본키(@Id) 타입

    // 이 인터페이스는 JpaRepository 상속을 통해 다음과 같은 기본 메서드들을 제공받음:
    // - save(): 엔티티 저장 및 수정
    // - deleteById(): ID로 삭제
    // - findById(): ID로 조회
    // - findAll(): 전체 조회
    // - count(): 개수 조회
}