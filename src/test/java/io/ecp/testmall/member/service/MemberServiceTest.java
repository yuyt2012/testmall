package io.ecp.testmall.member.service;

import io.ecp.testmall.member.entity.*;
import io.ecp.testmall.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MemberServiceTest {

    private MemberService memberService;

    private PasswordEncoder passwordEncoder;

    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        passwordEncoder = new BCryptPasswordEncoder();
        memberService = new MemberService(memberRepository, passwordEncoder);
    }

    @Test
    public void testSaveMember() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setName("Test Name");
        memberDTO.setEmail("test@example.com");
        memberDTO.setPassword("testPassword");
        memberDTO.setSocialLogin("testSocialLogin");
        memberDTO.setPhone("1234567890");
        memberDTO.setCity("Test City");
        memberDTO.setStreet("Test Street");
        memberDTO.setZipcode("12345");

        Member expectedMember = Member.builder()
                .name(memberDTO.getName())
                .email(memberDTO.getEmail())
                .password(passwordEncoder.encode(memberDTO.getPassword())) // Use passwordEncoder.encode
                .socialLogin(memberDTO.getSocialLogin())
                .phone(memberDTO.getPhone())
                .role(Role.USER)
                .address(Address.builder()
                        .city(memberDTO.getCity())
                        .street(memberDTO.getStreet())
                        .zipcode(memberDTO.getZipcode())
                        .build())
                .build();

        when(memberRepository.save(any(Member.class))).thenReturn(expectedMember);

        Member actualMember = memberService.saveMember(memberDTO);

        assertEquals(expectedMember, actualMember);
    }
};