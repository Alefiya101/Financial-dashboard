package in.alu.dto;

import in.alu.model.Role;

public class UserRegistrationDTO {

    private String username;
    private String password;
    private Role role;

    // 1. No-args constructor
    public UserRegistrationDTO() {}

    // 2. Full constructor
    public UserRegistrationDTO(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // 3. Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}