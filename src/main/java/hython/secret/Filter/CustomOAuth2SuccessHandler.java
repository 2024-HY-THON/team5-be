package hython.secret.Filter;

import hython.secret.DTO.CustomUserDetail;
import hython.secret.Entity.RefreshToken;
import hython.secret.Entity.User;
import hython.secret.Repository.RefreshTokenRepository;
import hython.secret.Repository.UserRepository;
import hython.secret.Service.RegisterService;
import hython.secret.Util.CookieUtil;
import hython.secret.Util.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final RefreshTokenRepository refreshRepository;
    private final Long refreshMs = 24 * 60 * 60 * 1000L;    // 24시간
    private final UserRepository userRepository;
    private final RegisterService registerService;

    public CustomOAuth2SuccessHandler(JwtUtil jwtUtil, CookieUtil cookieUtil, RefreshTokenRepository refreshRepository, UserRepository userRepository, RegisterService registerService) {
        this.jwtUtil = jwtUtil;
        this.cookieUtil = cookieUtil;
        this.refreshRepository = refreshRepository;
        this.userRepository = userRepository;
        this.registerService = registerService;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();

        String email = customUserDetail.getUsername();
        User user = userRepository.findByEmail(email);
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        // 세션에서 신규 사용자 여부 확인
        HttpSession session = request.getSession(false);
        String refresh = session != null ? (String) session.getAttribute("refresh") : null;


        if (refresh == null) {
            refresh = jwtUtil.createJwt("refresh", email, role, refreshMs);

            //Refresh 토큰 저장
            addRefreshEntity(email, user, refresh, refreshMs);
            response.addCookie(cookieUtil.createCookie("refresh", refresh));
        }

        if (session != null && Boolean.TRUE.equals(session.getAttribute("isNewUser"))){
            session.removeAttribute("email");
            // 새로운 사용자는 비밀번호 설정을 마친 후 다시 로그인 후 홈 화면으로 리다이렉트
            response.sendRedirect("http://localhost:3000/"); // 프론트쪽 특정 URI
        } else{
            // 기존 사용자는 바로 홈 화면으로 리다이렉트
            response.sendRedirect("http://localhost:8080/"); // 프론트쪽 특정 URI
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
