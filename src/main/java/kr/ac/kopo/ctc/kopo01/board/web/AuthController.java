package kr.ac.kopo.ctc.kopo01.board.web;

import kr.ac.kopo.ctc.kopo01.board.config.JwtTokenService;
import kr.ac.kopo.ctc.kopo01.board.dto.AuthRequest;
import kr.ac.kopo.ctc.kopo01.board.dto.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // no usages 🔧 touch011
@RequestMapping("/auth")
public class AuthController {

    @Autowired // 3 usages
    private JwtTokenService jwtTokenService;

    @PostMapping("/register") // no usages new *
    public String registerUser(@RequestBody AuthRequest authRequest) {
        return jwtTokenService.registerUser(authRequest);
    }

    @PostMapping("/authenticate") // no usages new *
    @ResponseBody
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = jwtTokenService.authenticateUser(authRequest);
        if (jwtTokenService.isUserAuthenticated()) {
            return ResponseEntity.ok(authResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authResponse);
        }
    }
}
