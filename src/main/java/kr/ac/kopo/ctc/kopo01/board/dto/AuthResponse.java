package kr.ac.kopo.ctc.kopo01.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthResponse {
    private String accessToken;
    private String tokenType;
    private String message; // 인증 실패시 메세지
}
