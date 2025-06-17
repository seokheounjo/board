package kr.ac.kopo.ctc.kopo01.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // CSRF 보호 비활성화 (운영 환경에서는 활성화 권장)

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/WEB-INF/views/index.jsp", "/public/**").permitAll()  // 모든 사용자 허용
                        .requestMatchers("/login", "doLogin", "/WEB-INF/views/login.jsp").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")  // ADMIN 역할만 허용
                        .anyRequest().authenticated()  // 나머지는 인증 필요
                )

                .formLogin(form -> form
                        .loginPage("/login")  // 커스텀 로그인 페이지
                        .loginProcessingUrl("/doLogin")  // 로그인 처리 URL
                        .defaultSuccessUrl("/home", true)  // 로그인 성공 시 이동할 기본 URL
                        .failureUrl("/login?error=true")  // 로그인 실패 시 이동 URL
                        .permitAll()  // 로그인 관련 페이지 모두 허용
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")  // 로그아웃 처리 URL
                        .logoutSuccessUrl("/login?logout=true")  // 로그아웃 성공 시 이동 URL
                        .permitAll()  // 로그아웃 관련 페이지 모두 허용
                );

        return http.build();
    }
}

