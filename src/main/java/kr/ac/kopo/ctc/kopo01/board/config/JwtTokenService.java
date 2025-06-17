package kr.ac.kopo.ctc.kopo01.board.config;


import kr.ac.kopo.ctc.kopo01.board.domain.board.User;
import kr.ac.kopo.ctc.kopo01.board.dto.AuthRequest;
import kr.ac.kopo.ctc.kopo01.board.dto.AuthResponse;
import kr.ac.kopo.ctc.kopo01.board.jwt.JwtTokenProvider;
import kr.ac.kopo.ctc.kopo01.board.repository.board.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public String registerUser(AuthRequest authRequest) {
        if (userRepository.findByUsername(authRequest.getUsername()).isPresent()) {
            return "Username already exists!"; // 또는 적절한 에러 응답
        }

        User newUser = new User();
        newUser.setUsername(authRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(authRequest.getPassword())); // 비밀번호 암호화
        newUser.setRoles("USER"); // 기본 역할 부여 (필요에 따라 admin 역할도 부여 가능)
        userRepository.save(newUser);

        return "User registered successfully!";
    }

    public AuthResponse authenticateUser(AuthRequest authRequest) {
        try {
            // AuthenticationManager를 통해 인증 시도
            // 이 과정에서 UserDetailsService가 호출되어 사용자 정보를 로드하고,
            // PasswordEncoder를 통해 비밀번호를 검증합니다.
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );

            // JWT 토큰 생성
            String jwt = jwtTokenProvider.generateToken(authentication);

            // 클라이언트에게 토큰 반환
            return new AuthResponse(jwt, "Bearer", null);

        } catch (Exception e) {
            // 인증 실패 시 (비밀번호 불일치, 사용자 없음 등)
            return new AuthResponse(null, null, "인증 실패: " + e.getMessage());
        }
    }

    public boolean isUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
    }

}
