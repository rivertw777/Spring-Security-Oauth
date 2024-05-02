package spring.oauth.config.oauth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import spring.oauth.config.auth.CustomUserDetails;
import spring.oauth.config.jwt.TokenProvider;

@Component
@Transactional
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String accessToken = tokenProvider.generateToken(userDetails);
        String uri = createURI(accessToken).toString();
        getRedirectStrategy().sendRedirect(request, response, uri);
    }

    // Redirect URI 생성. JWT를 쿼리 파라미터로 담아 전달한다.
    private URI createURI(String accessToken) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("access_token", accessToken);
        return UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/")
                .queryParams(queryParams)
                .build()
                .toUri();
    }

}