package hython.secret.Controller;

import hython.secret.API.ApiResponseDTO;
import hython.secret.DTO.NicknameDTO;
import hython.secret.Service.VerifySevice;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/register")
public class RegisterController {

    private final VerifySevice verifySevice;

    public RegisterController(VerifySevice verifySevice) {
        this.verifySevice = verifySevice;
    }

    // OAuth2 방식으로 처음 로그인 시도 할 시 비밀번호 업데이트 함.
    @Operation(summary = "OAuth2 비밀번호 설정 페이지", description = "소셜계정 로그인을 처음 시도했다면" +
            "비밀번호 설정 페이지로 이동합니다.")
    @GetMapping("/setNickname")
    public ResponseEntity<ApiResponseDTO<Void>> joinPassword(){
        ApiResponseDTO<Void> response = new ApiResponseDTO<>("success","oAuth2 소셜 로그인 닉네임 설정 페이지 성공", null);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/setNickname")
    public ResponseEntity<ApiResponseDTO<Void>> setNickname(@RequestParam NicknameDTO request, HttpSession session) {

        String nickname = request.getNickname();

        String email = (String) session.getAttribute("email");

        if(email == null){
            return ResponseEntity.badRequest().body(new ApiResponseDTO<>("error", "세션에 email이 없습니다.", null));
        }

        if(verifySevice.updateNickname(nickname,email)){
            session.removeAttribute("email");
            return ResponseEntity.ok(new ApiResponseDTO<>("success", "닉네임이 성공적으로 설정되었습니다.", null));
        } else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO<>("error", "닉네임 설정 중 서버에 오류가 발생했습니다.", null));

        }
    }
}
