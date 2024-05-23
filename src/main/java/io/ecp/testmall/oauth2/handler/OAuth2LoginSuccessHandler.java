package io.ecp.testmall.oauth2.handler;

import io.ecp.testmall.jwt.utils.JwtUtils;
import io.ecp.testmall.member.entity.PrincipalDetail;
import io.ecp.testmall.member.repository.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private MemberRepository memberRepository;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        PrincipalDetail principalDetail = (PrincipalDetail) authentication.getPrincipal();
        String email = principalDetail.getUsername();

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("role", principalDetail.getMember().getRole().toString());
        String token = JwtUtils.generateToken(claims, 60);

        if (memberRepository.findByEmail(email).isPresent()) {
            Cookie cookie = new Cookie("Authorization", "Bearer " + token);
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            response.addCookie(cookie);

            // 사용자 이름을 응답 바디에 저장
            response.getWriter().write("{\"user\":\"" + URLEncoder.encode(principalDetail.getMember().getName(), StandardCharsets.UTF_8.name()) + "\"}");
        } else {
            // 카카오 사용자 정보를 쿼리 파라미터로 추가
            String socialId = principalDetail.getMember().getSocialId();
            response.sendRedirect("http://localhost:5173/signup?email=" + email + "&socialId=" + socialId);
        }
    }
}