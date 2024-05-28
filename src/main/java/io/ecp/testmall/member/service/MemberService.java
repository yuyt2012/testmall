package io.ecp.testmall.member.service;

import io.ecp.testmall.member.Exception.CustomNotFountException;
import io.ecp.testmall.member.entity.*;
import io.ecp.testmall.member.repository.MemberRepository;
import io.ecp.testmall.utils.StringUtils;
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
                .socialLogin(memberDTO.getSocialLogin())
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
        if (StringUtils.isNotBlank(updateMemberDTO.getPassword())) {
            member.setPassword(updateMemberDTO.getPassword());
        }
        if (StringUtils.isNotBlank(updateMemberDTO.getName())) {
            member.setName(updateMemberDTO.getName());
        }
        if (StringUtils.isNotBlank(updateMemberDTO.getPhone())) {
            member.setPhone(updateMemberDTO.getPhone());
        }
        Address address = member.getAddress();
        if (StringUtils.isNotBlank(updateMemberDTO.getCity())) {
            address.setCity(updateMemberDTO.getCity());
        }
        if (StringUtils.isNotBlank(updateMemberDTO.getStreet())) {
            address.setStreet(updateMemberDTO.getStreet());
        }
        if (StringUtils.isNotBlank(updateMemberDTO.getZipcode())) {
            address.setZipcode(updateMemberDTO.getZipcode());
        }
        member.setAddress(address);
        return memberRepository.save(member);
    }
}
