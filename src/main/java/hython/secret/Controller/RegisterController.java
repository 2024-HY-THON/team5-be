package hython.secret.Controller;

import hython.secret.API.ApiResponseDTO;
import hython.secret.Service.VerifySevice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/register")
public class RegisterController {

    private final VerifySevice verifySevice;

    public RegisterController(VerifySevice verifySevice) {
        this.verifySevice = verifySevice;
    }

    // OAuth2 방식으로 처음 로그인 시도 할 시 닉네임 설정 함.
    @Operation(summary = "OAuth2 닉네임 설정 페이지", description = "소셜계정 로그인을 처음 시도했다면" +
            "닉네임 설정 페이지로 이동합니다.")
    @GetMapping("/setNickname")
    public ResponseEntity<ApiResponseDTO<Void>> joinPassword(){
        ApiResponseDTO<Void> response = new ApiResponseDTO<>("success","oAuth2 소셜 로그인 닉네임 설정 페이지 성공", null, null);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "OAuth2 닉네임 설정 처리", description = """
        소셜 회원의 닉네임을 설정합니다. 닉네임을 JSON 형식으로 서버에 전달합니다.
        인증은 세션을 통해 진행되며, 닉네임 재설정 후 소셜 로그인을 다시 진행해야 합니다.
        """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "닉네임 설정 성공"),
            @ApiResponse(responseCode = "400", description = "닉네임 전달 오류"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/setNicknameProc")
    public ResponseEntity<ApiResponseDTO<Void>> setNickname(@RequestParam String nickname, HttpSession session) {

        String email = (String) session.getAttribute("email");

        if(email == null){
            return ResponseEntity.badRequest().body(new ApiResponseDTO<>("error", "세션에 email이 없습니다.", null, null));
        }

        if(verifySevice.updateNickname(nickname,email)){
            session.removeAttribute("email");
            return ResponseEntity.ok(new ApiResponseDTO<>("success", "닉네임이 성공적으로 설정되었습니다.", null, null));
        } else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponseDTO<>("error", "닉네임 설정 중 서버에 오류가 발생했습니다.", null, null));

        }
    }
}
