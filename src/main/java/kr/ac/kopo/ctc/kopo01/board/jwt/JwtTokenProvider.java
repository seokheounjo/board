package kr.ac.kopo.ctc.kopo01.board.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key secretKey; // JWT 서명 키
    private final long accessTokenValidityInMilliseconds; // 토큰 유효 시간, 15분 (900000)

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey,
                            @Value("${jwt.access-token-expiration}") long accessTokenValidityInSeconds) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenValidityInMilliseconds = accessTokenValidityInSeconds * 1000;
    }

    // 1. JWT 토큰 생성
    public String generateToken(Authentication authentication) {
        // 인증된 사용자의 권한 가져오기 (예: "ROLE_USER,ROLE_ADMIN")
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.accessTokenValidityInMilliseconds); // 토큰 만료 시간

        return Jwts.builder()
                .setSubject(authentication.getName()) // 토큰 제목 (사용자 ID)
                .claim("auth", authorities) // 권한 정보 (클레임으로 추가)
                .setExpiration(validity) // 만료 시간
                .signWith(secretKey, SignatureAlgorithm.HS512) // 사용할 암호화 알고리즘과 키
                .compact(); // 토큰 생성
    }

    // 2. JWT 토큰으로 인증 정보 조회
    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token); // 토큰에서 클레임(Claim) 추출

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체 생성 (사용자 이름, 빈 비밀번호, 권한 목록)
        // JWT 기반 인증에서는 비밀번호가 토큰에 포함되지 않으므로 빈 문자열 사용
        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 3. JWT 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    // 토큰에서 클레임 추출 (예외 처리 포함)
    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            // 만료된 토큰의 경우에도 클레임을 반환하도록 처리 (로그인 갱신 등 로직에서 활용 가능)
            return e.getClaims();
        }
    }
}
