package hython.secret;

import hython.secret.Filter.CustomLogoutFilter;
import hython.secret.Filter.CustomOAuth2FailureHandler;
import hython.secret.Filter.CustomOAuth2SuccessHandler;
import hython.secret.Filter.JWTFilter;
import hython.secret.Repository.RefreshTokenRepository;
import hython.secret.Service.CustomOauth2UserService;
import hython.secret.Util.CookieUtil;
import hython.secret.Util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;


@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOauth2UserService customOauth2UserService;
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;
    private final CustomOAuth2FailureHandler customOAuth2FailureHandler;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshRepository;
    private final CookieUtil cookieUtil;

    @Autowired
    public SecurityConfig(CustomOauth2UserService customOauth2UserService, CustomOAuth2SuccessHandler customOAuth2SuccessHandler, CustomOAuth2FailureHandler customOAuth2FailureHandler, JwtUtil jwtUtil, RefreshTokenRepository refreshRepository, CookieUtil cookieUtil) {
        this.customOauth2UserService = customOauth2UserService;
        this.customOAuth2SuccessHandler = customOAuth2SuccessHandler;
        this.customOAuth2FailureHandler = customOAuth2FailureHandler;
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
        this.cookieUtil = cookieUtil;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors((cors) -> cors
                        .configurationSource(new CorsConfigurationSource() {
                            @Override
                            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                                CorsConfiguration config = new CorsConfiguration();


                                config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                                config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); // 허용할 메소드 Get ect on
                                config.setAllowCredentials(true);
                                config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
                                config.setExposedHeaders(Arrays.asList("Authorization"));
                                config.setMaxAge(3600L);

                                return config;
                            }
                        }));
        http
                .csrf((csrf) -> csrf.disable());
        http
                .formLogin((formLogin) -> formLogin.disable());

        http
                .authorizeHttpRequests((auth) -> auth

                        .requestMatchers("/test","/login/**","/auth/**","/register/**",
                                "/v3/api-docs/**", "/swagger/**", "/swagger-ui/**", "/belogs/**").permitAll()
                        .anyRequest().authenticated()
                );

        http
                .addFilterAfter(new JWTFilter(jwtUtil), OAuth2LoginAuthenticationFilter.class);

        http
                .addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class);


        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                );

        /**
         * 클라이언트 측에서 JWT 삭제:
         * 사용자가 로그아웃 버튼을 클릭하면, 클라이언트 측에서 JWT를 저장한 곳 (쿠키, localStorage 등)에서 해당 JWT를 삭제합니다.
         * */

        // oAuth2 방식
        http
                .oauth2Login((oauth) -> oauth
                        .userInfoEndpoint((userInfo) -> {
                            try {
                                userInfo.userService(customOauth2UserService);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        })
                        .successHandler(customOAuth2SuccessHandler)
                        .failureHandler(customOAuth2FailureHandler)
                );

//        http
//                .exceptionHandling(exception -> {
//                    exception.authenticationEntryPoint((request, response, authException) -> {
//                        response.setContentType("application/json");
//                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                        response.getWriter().write("{\"error\":\"Unauthorized\",\"message\":\"" + authException.getMessage() + "\"}");
//                    });
//                });

        return http.build();
    }

}
