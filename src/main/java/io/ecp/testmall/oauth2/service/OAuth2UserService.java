package io.ecp.testmall.oauth2.service;

import io.ecp.testmall.member.entity.Member;
import io.ecp.testmall.member.entity.PrincipalDetail;
import io.ecp.testmall.member.entity.Role;
import io.ecp.testmall.member.repository.MemberRepository;
import io.ecp.testmall.oauth2.user.KakaoUserInfo;

import java.util.Base64;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional(readOnly = true)
public class OAuth2UserService extends DefaultOAuth2UserService {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Autowired
    private MemberRepository memberRepository;

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Transactional(readOnly = false)
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        log.info("OAuth2User: {}", oAuth2User);
        log.info("attributes: {}", attributes);

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
        log.info("userNameAttributeName: {}", userNameAttributeName);

        KakaoUserInfo kakaoUserInfo = new KakaoUserInfo(attributes);
        String email = kakaoUserInfo.getEmail();

        Optional<Member> memberOptional = memberRepository.findByEmail(email);

        Member member;
        // 가입되지 않은 회원인 경우, 새로운 Member 객체를 생성하지만 저장하지 않습니다.
        member = memberOptional.orElseGet(() -> Member.builder()
                .email(kakaoUserInfo.getEmail())
                .role(Role.USER)
                .build());

        return new PrincipalDetail(member,
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().getName())), attributes);
    }

    @Transactional(readOnly = false)
    public Member saveKakaoUser(String socialId, String email) {
        Member newMember = Member.builder()
                .email(email)
                .role(Role.USER)
                .build();
        return memberRepository.save(newMember);
    }
}
