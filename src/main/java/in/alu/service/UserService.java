package in.alu.service;

import in.alu.dto.UserRegistrationDTO;
import in.alu.dto.UserResponseDTO;
import in.alu.model.User;
import in.alu.model.Role;
import in.alu.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor injection is better than @Autowired on fields
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO registerUser(UserRegistrationDTO registrationDTO) {
        // 1. Check if user exists
        if (userRepository.findByUsername(registrationDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists!");
        }

        // 2. Map DTO to Entity
        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        
        // 3. HASH THE PASSWORD (Never store plain text)
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        
        // 4. Handle Role String to Enum conversion
        try {
        	user.setRole(registrationDTO.getRole());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid Role provided.");
        }

        // 5. Save to Database
        User savedUser = userRepository.save(user);

        // 6. Map back to ResponseDTO (Do NOT return the password)
        return new UserResponseDTO(
            savedUser.getId(), 
            savedUser.getUsername(), 
            savedUser.getRole()
        );
    }
}