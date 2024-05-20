package io.ecp.testmall.oauth2.controller;

import io.ecp.testmall.oauth2.service.OAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class Oauth2Controller {

    @Autowired
    private OAuth2UserService oAuth2UserService;

    @PostMapping("/oauth2/callback/kakao")
    public ResponseEntity<?> handleKakaoCallback(@RequestBody Map<String, String> data) {
        String code = data.get("code");
        if (code != null) {
            try {
                String accessToken = oAuth2UserService.getAccessToken(code);
                boolean exist = oAuth2UserService.isUserExistByToken(accessToken);
                // 사용자 정보가 데이터베이스에 없는 경우
                if (!exist) {
                    return ResponseEntity.status(HttpStatus.OK).body("회원가입 필요");
                }
                // 사용자 정보가 데이터베이스에 있는 경우
                else {
                    return ResponseEntity.ok().body("로그인 성공");
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("인증 코드 처리 중 오류 발생");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 코드가 없습니다");
        }
    }
}