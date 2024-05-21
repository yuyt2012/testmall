package io.ecp.testmall.member.service;

import io.ecp.testmall.member.entity.Address;
import io.ecp.testmall.member.entity.Member;
import io.ecp.testmall.member.entity.MemberDTO;
import io.ecp.testmall.member.entity.Role;
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
}
