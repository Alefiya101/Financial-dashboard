package in.alu.controller;

import in.alu.dto.LoginRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginDTO) {
        // 1. Attempt to authenticate
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(), 
                loginDTO.getPassword()
            )
        );

        // 2. If successful, set the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok("Login successful! Welcome " + loginDTO.getUsername());
    }
}