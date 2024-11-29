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
        Refresh Token을 사용하여 새로운 Access Token을 발급받습니다.
        만료된 Access Token을 대체하기 위해 사용됩니다.
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
                , null);
                //response status code
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
            }

            //DB에 저장되어 있는지 확인
            Boolean isExist = refreshRepository.existsByRefreshToken(refresh);
            if (!isExist){
                ApiResponseDTO<Map<String,String>> responseDTO = new ApiResponseDTO<>(
                        "error","invalid refresh token", null
                , null);
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
            , null);

            return ResponseEntity.ok(responseDTO);
        } catch (ExpiredJwtException e){
            ApiResponseDTO<Map<String,String>> responseDTO = new ApiResponseDTO<>("error","Refresh Token이 만료되었습니다. 다시 로그인 해주세요", null, null);
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
