package io.ecp.testmall.oauth2.controller;

import io.ecp.testmall.jwt.utils.JwtUtils;
import io.ecp.testmall.member.entity.Member;
import io.ecp.testmall.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5174")
public class OAuth2Controller {

    @Autowired
    private MemberRepository memberRepository;

    @GetMapping("/kakaoLoginSuccess")
    public ResponseEntity<?> kakaoLoginSuccess(@RequestParam String email, @RequestHeader("Authorization") String token) {
        String t = JwtUtils.extractToken(token);
        if (!JwtUtils.validateToken(t)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 유효하지 않습니다.");
        }

        Optional<Member> byEmail = memberRepository.findByEmail(email);
        if (byEmail.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body("회원가입 필요");
        }

        return ResponseEntity.status(HttpStatus.OK).body("로그인 성공");
    }
}
