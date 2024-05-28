package io.ecp.testmall.oauth2.controller;

import io.ecp.testmall.jwt.utils.JwtUtils;
import io.ecp.testmall.member.entity.Member;
import io.ecp.testmall.member.entity.MemberDTO;
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
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "회원가입 필요", "email", email));
        }
        Member member = memberRepository.findByEmail(email).get();

        MemberDTO memberInfo = getMemberInfo(member);
        String role = member.getRole().toString();
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "로그인 성공", "user", memberInfo, "role", role));
    }

    private static MemberDTO getMemberInfo(Member member) {
        MemberDTO memberInfo = new MemberDTO();
        memberInfo.setEmail(member.getEmail());
        memberInfo.setPassword(member.getPassword());
        memberInfo.setName(member.getName());
        memberInfo.setPhone(member.getPhone());
        memberInfo.setCity(member.getAddress().getCity());
        memberInfo.setStreet(member.getAddress().getStreet());
        memberInfo.setZipcode(member.getAddress().getZipcode());
        memberInfo.setSocialLogin(member.getSocialLogin());
        return memberInfo;
    }
}
