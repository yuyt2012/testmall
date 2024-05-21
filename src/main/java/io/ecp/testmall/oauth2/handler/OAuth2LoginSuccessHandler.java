package io.ecp.testmall.oauth2.handler;

import io.ecp.testmall.member.entity.PrincipalDetail;
import io.ecp.testmall.member.repository.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        PrincipalDetail principalDetail = (PrincipalDetail) authentication.getPrincipal();
        String email = principalDetail.getUsername();

        if (memberRepository.findByEmail(email).isPresent()) {
            response.sendRedirect("http://localhost:5173/home");
        } else {
            // 카카오 사용자 정보를 쿼리 파라미터로 추가
            String socialId = principalDetail.getMember().getSocialId();
            response.sendRedirect("http://localhost:5173/signup?email=" + email + "&socialId=" + socialId);
        }
    }
}