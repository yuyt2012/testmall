package io.ecp.testmall.member.controller;

import io.ecp.testmall.member.entity.Member;
import io.ecp.testmall.member.entity.MemberDTO;
import io.ecp.testmall.member.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    private Logger log = LoggerFactory.getLogger(getClass());

    @PostMapping("/signUp")
    public Map<String, String> signUp(@RequestBody MemberDTO memberDTO) {
        log.info("--------------------------- MemberController ---------------------------");
        log.info("memberDTO = {}", memberDTO);
        Map<String, String> response = new HashMap<>();
        Member byEmail = memberService.findByEmail(memberDTO.getEmail())
                .orElse(null);
        if (byEmail != null) {
            response.put("error", "이미 존재하는 이메일입니다");
        } else {
            memberService.saveMember(memberDTO);
            response.put("success", "성공적으로 처리하였습니다");
        }
        return response;
    }

}