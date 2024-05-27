package io.ecp.testmall.member.controller;

import io.ecp.testmall.jwt.utils.JwtUtils;
import io.ecp.testmall.member.Exception.CustomNotFountException;
import io.ecp.testmall.member.entity.*;
import io.ecp.testmall.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
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
@CrossOrigin(origins = "http://localhost:5174")
public class MemberController {

    @Autowired
    private MemberService memberService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody MemberDTO memberDTO) {
        Member byEmail = memberService.findByEmail(memberDTO.getEmail())
                .orElse(null);
        if (byEmail != null) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Map.of("success", false, "error", "이미 가입된 이메일입니다."));
        } else {
            memberService.saveMember(memberDTO);
            return ResponseEntity.ok().body(Map.of("success", true, "message", "회원가입 성공"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginForm loginForm, HttpServletResponse response) {
        Optional<Member> optionalMember = memberService.findByEmail(loginForm.getEmail());
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            if (passwordEncoder.matches(loginForm.getPassword(), member.getPassword())) {
                Map<String, Object> claims = new HashMap<>();
                claims.put("email", member.getEmail());
                claims.put("role", member.getRole().toString());
                String token = JwtUtils.generateToken(claims, 60); // 60분 동안 유효한 토큰 생성

                response.addHeader("Authorization", "Bearer " + token);
                response.addHeader("Access-Control-Expose-Headers", "Authorization");

                MemberDTO memberInfo = new MemberDTO();
                memberInfo.setEmail(member.getEmail());
                memberInfo.setName(member.getName());
                memberInfo.setSocialId(member.getSocialId());
                memberInfo.setPhone(member.getPhone());
                memberInfo.setCity(member.getAddress().getCity());
                memberInfo.setStreet(member.getAddress().getStreet());
                memberInfo.setZipcode(member.getAddress().getZipcode());
                String role = member.getRole().toString();
                return ResponseEntity.ok().body(Map.of("success", true, "user", memberInfo, "role", role));
            }
        }
        return ResponseEntity.ok().body(Map.of("success", false, "message", "이메일 또는 비밀번호가 잘못되었습니다."));
    }

    @GetMapping("/userInfo")
    public ResponseEntity<?> getUserInfo(@RequestParam String email) {

        Member member = memberService.findByEmail(email)
                .orElseThrow(() -> new CustomNotFountException("해당 이메일을 가진 회원이 없습니다."));

        MemberDTO kakaoMemberInfo = new MemberDTO();
        kakaoMemberInfo.setEmail(member.getEmail());
        kakaoMemberInfo.setName(member.getName());
        kakaoMemberInfo.setSocialId(member.getSocialId());
        kakaoMemberInfo.setPhone(member.getPhone());
        kakaoMemberInfo.setCity(member.getAddress().getCity());
        kakaoMemberInfo.setStreet(member.getAddress().getStreet());
        kakaoMemberInfo.setZipcode(member.getAddress().getZipcode());
        String role = member.getRole().toString();
        return ResponseEntity.ok().body(Map.of("success", true, "user", kakaoMemberInfo, "role", role));
    }

    @PatchMapping("/update")
    public ResponseEntity<?> update(@RequestBody UpdateMemberDTO updateMemberDTO, @RequestHeader("Authorization") String token) {
        String t = JwtUtils.extractToken(token);
        if (!JwtUtils.validateToken(t)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 유효하지 않습니다.");
        }
        try {
            memberService.updateMember(updateMemberDTO.getEmail(), updateMemberDTO);
            return ResponseEntity.ok().body(Map.of("success", true, "message", "회원 정보 수정 성공"));
        } catch (CustomNotFountException e) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Map.of("success", false, "error", e.getMessage()));
        }
    }
}