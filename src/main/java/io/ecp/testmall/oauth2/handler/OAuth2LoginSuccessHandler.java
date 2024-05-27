package io.ecp.testmall.oauth2.handler;

import io.ecp.testmall.jwt.utils.JwtUtils;
import io.ecp.testmall.member.entity.PrincipalDetail;
import io.ecp.testmall.member.repository.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
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

        response.sendRedirect("http://localhost:5173/kakaoLoginSuccess?token=" + "Bearer " + token);
    }
}