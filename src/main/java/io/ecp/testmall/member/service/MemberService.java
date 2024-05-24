package io.ecp.testmall.member.service;

import io.ecp.testmall.member.Exception.CustomNotFountException;
import io.ecp.testmall.member.entity.*;
import io.ecp.testmall.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Transactional(readOnly = false)
    public Member saveMember(MemberDTO memberDTO) {
        Member member = Member.builder()
                .name(memberDTO.getName())
                .email(memberDTO.getEmail())
                .password(passwordEncoder.encode(memberDTO.getPassword()))
                .socialId(memberDTO.getSocialId())
                .phone(memberDTO.getPhone())
                .role(Role.USER)
                .address(Address.builder()
                        .city(memberDTO.getCity())
                        .street(memberDTO.getStreet())
                        .zipcode(memberDTO.getZipcode())
                        .build())
                .build();
        return memberRepository.save(member);
    }

    @Transactional(readOnly = false)
    public Member updateMember(String email, UpdateMemberDTO updateMemberDTO) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomNotFountException("해당 이메일을 가진 회원이 없습니다."));
        return member.update(updateMemberDTO);
    }
}
