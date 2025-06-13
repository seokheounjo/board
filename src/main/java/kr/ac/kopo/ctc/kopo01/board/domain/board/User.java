package kr.ac.kopo.ctc.kopo01.board.domain.board;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // AUTO_INCREMENT와 같은 DB의 자동 증가 컬럼을 사용하여 기본키를 생성
    // MySQL의 경우 AUTO_INCREMENT, PostgreSQL의 경우 SERIAL 사용
    // 각 DB 벤더의 자동 증가 전략을 따르므로 DB에 위임하는 방식
    // INSERT 시점에 DB가 자동으로 값을 할당

    private Long id;

    private String username;

    private LocalDate joindate;
}
