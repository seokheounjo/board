package kr.ac.kopo.ctc.kopo01.board.config;

import kr.ac.kopo.ctc.kopo01.board.domain.board.User;
import kr.ac.kopo.ctc.kopo01.board.repository.board.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            if (userRepository.findByUsername("admin").isEmpty()) {
                User adminUser = new User();
                adminUser.setUsername("admin");
                adminUser.setPassword(passwordEncoder.encode("admin"));  // 비밀번호 암호화
                adminUser.setRoles("ADMIN,USER");  // 복수 역할 부여
                userRepository.save(adminUser);
                System.out.println("Admin user created: admin");
            }

            if (userRepository.findByUsername("user").isEmpty()) {
                User regularUser = new User();
                regularUser.setUsername("user");
                regularUser.setPassword(passwordEncoder.encode("user"));  // 비밀번호 암호화
                regularUser.setRoles("USER");  // 일반 사용자 권한
                userRepository.save(regularUser);
                System.out.println("Regular user created: user");
            }

        };
    }
}
