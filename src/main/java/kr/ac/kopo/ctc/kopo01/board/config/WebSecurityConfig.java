package kr.ac.kopo.ctc.kopo01.board.config;

import kr.ac.kopo.ctc.kopo01.board.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화 (개발 시 편의를 위함, 실제 운영 환경에서는 신중하게 고려)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/WEB-INF/views/index.jsp", "/public/**").permitAll() // "/" 등 경로 아래는 모든 사용자 접근 허용
                        .requestMatchers("/login", "/doLogin", "/WEB-INF/views/login.jsp").permitAll() // "/" 등 경로 아래는 모든 사용자 접근 허용
                        .requestMatchers("/auth/register", "auth/authenticate").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN") // "/admin/" 경로 아래는 ADMIN 역할만 접근 허용
                        .anyRequest().authenticated() // 그 외 모든 요청은 인증된 사용자만 접근 허용
                )
                .formLogin(form -> form
                        .loginPage("/login") // 커스텀 로그인 페이지 URL 설정 (GET 요청)
                        .loginProcessingUrl("/doLogin") // 로그인 처리 URL 설정 (POST 요청, 실제 로그인 로직은 Spring Security가 처리)
                        .defaultSuccessUrl("/home", true) // 로그인 성공 시 이동할 기본 URL
                        .failureUrl("/login?error=true") // 로그인 실패 시 이동할 URL
                        .permitAll() // 로그인 관련 페이지는 모든 사용자 접근 허용
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 처리 URL 설정
                        .logoutSuccessUrl("/login?logout=true") // 로그아웃 성공 시 이동할 URL
                        .permitAll() // 로그아웃 관련 페이지는 모든 사용자 접근 허용
                )
                // LoginForm 인증 + Basic 인증
                .httpBasic(Customizer.withDefaults())
                // JWT 인증 필터를 Spring Security의 UsernamePasswordAuthenticationFilter 이전에 추가
                .addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }
}
