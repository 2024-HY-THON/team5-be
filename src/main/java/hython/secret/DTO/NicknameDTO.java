package hython.secret.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NicknameDTO {

    @NotBlank(message = "닉네임은 필수 입력")
    private String nickname;
}
