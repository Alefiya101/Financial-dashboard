package in.alu.dto;

import in.alu.model.Role;

public class UserResponseDTO {

    private Long id;
    private String username;
    private Role role;

    // 1. No-args constructor
    public UserResponseDTO() {}

    // 2. Full constructor
    public UserResponseDTO(Long id, String username, Role role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    // 3. Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}