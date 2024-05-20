package io.ecp.testmall.member.controller;

import io.ecp.testmall.jwt.utils.JwtUtils;
import io.ecp.testmall.member.entity.LoginForm;
import io.ecp.testmall.member.entity.Member;
import io.ecp.testmall.member.entity.MemberDTO;
import io.ecp.testmall.member.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberDTO memberDTO) {
        log.info("memberDTO = {}", memberDTO);
        Member byEmail = memberService.findByEmail(memberDTO.getEmail())
                .orElse(null);
        if (byEmail != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Map.of("success", false, "error", "이미 가입된 이메일입니다."));
        } else {
            memberService.saveMember(memberDTO);
            return ResponseEntity.ok().body(Map.of("success", true));
        }
    }

    @GetMapping("/check/{email}")
    public boolean checkEmail(@PathVariable String email) {
        Optional<Member> byEmail = memberService.findByEmail(email);
        return byEmail.isEmpty();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginForm loginForm) {
        Optional<Member> optionalMember = memberService.findByEmail(loginForm.getEmail());
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            if (passwordEncoder.matches(loginForm.getPassword(), member.getPassword())) {
                Map<String, Object> claims = new HashMap<>();
                claims.put("email", member.getEmail());
                claims.put("role", member.getRole().toString());
                String token = JwtUtils.generateToken(claims, 60); // 60분 동안 유효한 토큰 생성
                return ResponseEntity.ok().header("Authorization", "Bearer " + token).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}