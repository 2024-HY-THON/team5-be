package hython.secret;

import hython.secret.API.ApiResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiTest {

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 페이지 반환 성공"),
            @ApiResponse(responseCode = "400", description = "로그인 페이지 오류 발생")
    })
    @Operation(summary = "로그인 페이지", description = "로그인 페이지를 반환합니다. 로그인 오류가 있을 경우 오류 메시지를 포함합니다.")
    @GetMapping("/test")
    public ResponseEntity<ApiResponseDTO<Void>> showLogin(
            @Parameter(description = "몰라", required = false)
            @RequestParam(value = "error", required = false) String error) {
        ApiResponseDTO<Void> response = new ApiResponseDTO<>("success","안녕",null);

        return ResponseEntity.ok(response);
    }


}
