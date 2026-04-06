package in.alu.controller;

import in.alu.dto.UserRegistrationDTO;
import in.alu.dto.UserResponseDTO;
import in.alu.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRegistrationDTO registrationDTO) {
        // This will now work perfectly with the Role Enum fix we just did
        return ResponseEntity.ok(userService.registerUser(registrationDTO));
    }
}