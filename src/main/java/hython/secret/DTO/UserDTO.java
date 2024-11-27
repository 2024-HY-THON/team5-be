package hython.secret.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserDTO {

    private int user_id;
    private String username;
    private String userCode;
    private String role;
    private String platform;
    private String platform_id;
    private LocalDateTime joinDate;
    private String email;

    public UserDTO(String platform, String platform_id, String email, String name, String role, String userCode){
        this.platform = platform;
        this.platform_id = platform_id;
        this.userCode = userCode;
        this.role = role;
        this.username = name;
        this.email = email;
        this.joinDate = LocalDateTime.now();
    }

}
