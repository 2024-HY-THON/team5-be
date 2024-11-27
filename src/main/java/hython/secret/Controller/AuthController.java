package hython.secret.Controller;

import hython.secret.API.ApiResponseDTO;
import hython.secret.Entity.RefreshToken;
import hython.secret.Entity.User;
import hython.secret.Repository.RefreshTokenRepository;
import hython.secret.Repository.UserRepository;
import hython.secret.Util.CookieUtil;
import hython.secret.Util.ExpiredTime;
import hython.secret.Util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final RefreshTokenRepository refreshRepository;
    private final UserRepository userRepository;
    private final Long accessMs = ExpiredTime.accessMs;  // 1시간
    private final Long refreshMs = ExpiredTime.refreshMs;    // 24시간

    public AuthController(JwtUtil jwtUtil, CookieUtil cookieUtil, RefreshTokenRepository refreshRepository, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.cookieUtil = cookieUtil;
        this.refreshRepository = refreshRepository;
        this.userRepository = userRepository;
    }

    /**
     * Access Token 발급 api
     * */
    @Operation(summary = "Access Token 발급 api", description = """
               이 API는 프론트 개발자가 `Refresh Token`을 활용해 `Access Token`을 발급받기 위해 사용. 
               **OAuth2 소셜 로그인 사용자**는 첫 로그인 시 `Refresh Token`만 부여되므로, 권한 인증이 필요할 때 이 API를 호출하여 `Access Token`을 발급받아야 함.

               - **사용 방식**:
                   - 로그인 후, 모든 요청에 대해 `Access Token`을 사용합니다.
                   - 만료된 경우, 이 API를 통해 `Refresh Token`을 사용해 새 `Access Token`을 요청할 수 있음.
                   - **OAuth2 소셜 로그인 사용자**는 첫 로그인 시 `Access Token`이 발급되지 않으므로, 이 API를 사용해 필요한 `Access Token`을 발급받아야 합니다.
               """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access Token 발급 성공"),
            @ApiResponse(responseCode = "400", description = "Refresh Token 만료, 다시 로그인 해야함.")
    })
    @PostMapping("/access-token")
    public ResponseEntity<ApiResponseDTO<Map<String, String>>> generateAceesToken(@CookieValue("refresh") String refresh,
                                                                                  HttpServletResponse response){
        try{
            // Refresh Token의 만료여부 확인
            jwtUtil.isExpired(refresh);
            String email = jwtUtil.getUserEmail(refresh);
            String role = jwtUtil.getRole(refresh);
            User user = userRepository.findByEmail(email);

            String category = jwtUtil.getCategory(refresh);

            if (!category.equals("refresh")) {

                ApiResponseDTO<Map<String,String>> responseDTO = new ApiResponseDTO<>(
                        "error","invalid refresh token", null
                );
                //response status code
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
            }

            //DB에 저장되어 있는지 확인
            Boolean isExist = refreshRepository.existsByRefreshToken(refresh);
            if (!isExist){
                ApiResponseDTO<Map<String,String>> responseDTO = new ApiResponseDTO<>(
                        "error","invalid refresh token", null
                );
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
            }

            String newAccess = jwtUtil.createJwt("access", email, role, accessMs);
            String newRefresh = jwtUtil.createJwt("refresh", email, role, refreshMs);

            refreshRepository.deleteByEmail(refresh);
            addRefreshEntity(email, user, newAccess, refreshMs);

            Cookie refreshCookie = cookieUtil.createCookie("refresh", newRefresh); // access 토큰 재발급 할 때, refresh 토큰도 함께 재발급하여 쿠키로 전달함.
            response.addCookie(refreshCookie);

            Map<String, String> tokens = new HashMap<>();
            tokens.put("access", newAccess);

            ApiResponseDTO<Map<String,String>> responseDTO = new ApiResponseDTO<>(
                    "success","AccessToken이 성공적으로 발급되었습니다.", tokens
            );

            return ResponseEntity.ok(responseDTO);
        } catch (ExpiredJwtException e){
            ApiResponseDTO<Map<String,String>> responseDTO = new ApiResponseDTO<>("error","Refresh Token이 만료되었습니다. 다시 로그인 해주세요", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);
        }
    }


    private void addRefreshEntity(String email, User user, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);
        LocalDateTime expiresAt = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        RefreshToken refreshEntity = new RefreshToken();
        refreshEntity.setEmail(email);
        refreshEntity.setRefreshToken(refresh);
        refreshEntity.setExpires_at(expiresAt);
        refreshEntity.setUser(user);

        refreshRepository.save(refreshEntity);
    }
}
