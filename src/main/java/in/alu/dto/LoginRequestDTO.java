package in.alu.dto;

public class LoginRequestDTO {
    private String username;
    private String password;

    // No-args constructor
    public LoginRequestDTO() {}

    // Full constructor
    public LoginRequestDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}