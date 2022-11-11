package com.efub.cafetimes.config;

import com.efub.cafetimes.domain.User;
import com.efub.cafetimes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        User user = userRepository.findUserByKakaoId(oAuth2User.getAttribute("id"));

        log.info("Principal에서 꺼낸 OAuth2User = {}", oAuth2User);

        //로그인 성공 시 jwt 토큰 발행
        String accessToken = jwtTokenProvider.createAccessToken(oAuth2User.getAttribute("id").toString(), user.getRole());
        String refreshToken = jwtTokenProvider.createRefreshToken(oAuth2User.getAttribute("id").toString(), user.getRole());

        log.info("{}", accessToken);

        response.addHeader("X-AUTH-TOKEN", accessToken);
        response.addHeader("REFRESH-TOKEN", refreshToken);

        String redirectUrl = UriComponentsBuilder.fromUriString("https://localhost:3000/oauth2/redirect")
                .build().toUriString();

        if (response.isCommitted()) {
            logger.debug("응답이 이미 커밋된 상태입니다. " + redirectUrl + "로 리다이렉트하도록 바꿀 수 없습니다.");
            return;
        }

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
