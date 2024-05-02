package spring.oauth.config.jwt;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static spring.oauth.config.jwt.JwtProperties.HEADER_STRING;
import static spring.oauth.config.jwt.JwtProperties.TOKEN_PREFIX;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import spring.oauth.config.auth.CustomUserDetails;
import spring.oauth.config.auth.CustomUserDetailsService;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // 헤더에 토큰 정보 확인
        String header = request.getHeader(HEADER_STRING.getValue());
        if (header == null || !header.startsWith(TOKEN_PREFIX.getValue())) {
            chain.doFilter(request, response);
            return;
        }
        // 헤더에서 토큰 정보 추출
        String token = request.getHeader(HEADER_STRING.getValue()).replace(TOKEN_PREFIX.getValue(), "");

        try {
            // 토큰 검증
            tokenProvider.validateToken(token);

            // 인증 정보 추출
            Claims claims = tokenProvider.parseClaims(token);
            String userName = claims.getSubject();
            CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "",
                    userDetails.getAuthorities());

            // 사용자 인증 정보 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JwtException e) {
            response.setStatus(SC_UNAUTHORIZED);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(e.getMessage());
        }
        chain.doFilter(request, response);
    }

}
