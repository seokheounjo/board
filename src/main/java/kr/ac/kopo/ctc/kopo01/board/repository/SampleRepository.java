// 이 인터페이스는 board 모듈의 데이터 접근 레이어(repository)에 속함을 나타냄
package kr.ac.kopo.ctc.kopo01.board.repository;

// Sample 엔티티 클래스 import (DB 테이블과 매핑되는 자바 객체)
import kr.ac.kopo.ctc.kopo01.board.domain.Sample;

// Spring Data JPA에서 제공하는 페이징 및 정렬 기능을 위한 클래스들 import
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// Spring Data JPA의 핵심 인터페이스: 기본 CRUD 메서드를 제공
import org.springframework.data.jpa.repository.JpaRepository;

// 조건 기반 동적 쿼리를 위한 스펙 인터페이스
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

// 리스트, Optional 등 자바 컬렉션 관련 클래스 import
import java.util.List;
import java.util.Optional;

/**
 * SampleRepository는 Sample 엔티티에 대한 데이터 접근 기능을 정의하는 인터페이스이다.
 * JpaRepository를 상속받아 기본적인 CRUD 기능을 제공하며,
 * JpaSpecificationExecutor를 통해 복잡한 동적 조건 쿼리도 지원한다.
 */
public interface SampleRepository extends JpaRepository<Sample, Long>, JpaSpecificationExecutor<Sample> {

    /**
     * title이 특정 문자열과 일치하는 Sample 객체 하나를 Optional로 조회.
     * - 자동으로 WHERE title = ? 쿼리를 생성함
     * - 결과가 없을 수도 있으므로 Optional로 감싸 반환
     */
    Optional<Sample> findOneByTitle(String title);

    /**
     * title이 특정 값과 일치하는 Sample 객체들을 페이징 처리해서 조회.
     * - Pageable 객체를 통해 page 번호, 사이즈, 정렬을 전달받음
     * - 반환은 Page<Sample> 형식으로, 페이징 메타데이터도 포함됨
     */
    Page<Sample> findAllByTitle(String type, Pageable pageable);

    /**
     * title이 특정 값과 일치하는 모든 Sample 객체 리스트를 조회 (페이징 없이 전체 다 가져옴)
     * - 조건: title = ?
     */
    List<Sample> findAllByTitle(String type);
}
