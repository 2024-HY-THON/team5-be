package hython.secret.Service;

import hython.secret.DTO.CustomUserDetail;
import hython.secret.DTO.NaverUserDetails;
import hython.secret.DTO.UserDTO;
import hython.secret.Entity.User;
import hython.secret.Repository.OAuth2UserInfo;
import hython.secret.Repository.UserRepository;
import hython.secret.Util.CookieUtil;
import hython.secret.Util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final HttpSession httpSession;
    private final HttpServletResponse httpServletResponse;
    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final RegisterService registerService;


    private final Long refreshMs = 24 * 60 * 60 * 1000L;    // 24시간


    @Autowired
    public CustomOauth2UserService(UserRepository userRepository, UserService userService, HttpSession httpSession, HttpServletResponse httpServletResponse, JwtUtil jwtUtil, CookieUtil cookieUtil, RegisterService registerService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.httpSession = httpSession;
        this.httpServletResponse = httpServletResponse;
        this.jwtUtil = jwtUtil;
        this.cookieUtil = cookieUtil;
        this.registerService = registerService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest); //회원 프로필 조회

        try {
            return processOAuth2User(userRequest, oAuth2User);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) throws IOException {
        String platform = userRequest.getClientRegistration().getClientName();
        OAuth2UserInfo oAuth2UserInfo = null;


        // 플랫폼에 따라 OAuth2UserInfo 생성
        if ("Naver".equalsIgnoreCase(platform)) {
            log.info("네이버 로그인");
            oAuth2UserInfo = new NaverUserDetails(oAuth2User.getAttributes());
        } else {
            throw new OAuth2AuthenticationException("지원하지 않는 소셜 : " + platform);
        }

        if (oAuth2UserInfo == null) {
            throw new OAuth2AuthenticationException("OAuth2UserInfo is null");
        }

        String platform_id = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        String name = oAuth2UserInfo.getName();
        String role = "ROLE_USER";
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(email));

        User user;

        /* 만약 처음 로그인 시도 했으면 회원가입이 닉네임 세팅이
         * 필요하므로 관련된 로직으로 수정 */
        if(userOptional.isEmpty()){

            String userCode = RegisterService.generateCode(); // 만약 처음 로그인 시도 했으면 랜덤으로 userCode 배부

            UserDTO userDTO = new UserDTO(platform, platform_id, email, name, role, userCode);

            userService.saveUser(userDTO);
            log.info("새로운 사용자 저장: {}", email);

            // 세션에 진행 단계 저장
            httpSession.setAttribute("email", email);

            String refresh = jwtUtil.createJwt("refresh", email, role, refreshMs);
            httpSession.setAttribute("refreshToken", refresh);
            httpServletResponse.addCookie(cookieUtil.createCookie("refresh",refresh));

            try {
                httpServletResponse.sendRedirect("http://localhost:3001/setNickname"); // 클라이언트 개발자가 리다이렉트 할 URI
                throw new OAuth2AuthenticationException("닉네임 설정 페이지로 리다이렉트되었습니다."); // 리다이렉트를 수행했으므로 메서드 종료
            }  catch (IOException e) {
                log.error("리다이렉션 실패");
            }
        }

        user = userOptional.get();
        return new CustomUserDetail(user, oAuth2User.getAttributes());
    }
}
