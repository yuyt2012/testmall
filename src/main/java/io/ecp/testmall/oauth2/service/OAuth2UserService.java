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
        String socialId = kakaoUserInfo.getSocialId();

        Member member = memberRepository.findBySocialId(socialId).orElseThrow(
                () -> new IllegalArgumentException("가입되지 않은 회원입니다."));

        return new PrincipalDetail(member,
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().getName())), attributes);
    }

    @Transactional(readOnly = false)
    public Member saveKakaoUser(String socialId, String email) {
        Member newMember = Member.builder()
                .socialId(socialId)
                .email(email)
                .role(Role.USER)
                .build();
        return memberRepository.save(newMember);
    }


    public String getAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        String credentials = clientId;
        String encodedCredentials = new String(Base64.getEncoder().encode(credentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + encodedCredentials);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(OAuth2ParameterNames.GRANT_TYPE, "authorization_code");
        params.add(OAuth2ParameterNames.CODE, code);
        params.add(OAuth2ParameterNames.REDIRECT_URI, "http://localhost:8080/login/oauth2/code/kakao");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<OAuth2AccessTokenResponse> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token", HttpMethod.POST, request, OAuth2AccessTokenResponse.class);

        OAuth2AccessToken accessToken = response.getBody().getAccessToken();

        return accessToken.getTokenValue();
    }

    public boolean isUserExistByToken(String accessToken) {
        // 액세스 토큰을 사용하여 사용자의 이메일을 가져옵니다.
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.GET, request, Map.class);
        String email = (String) response.getBody().get("email");

        // 이메일을 사용하여 데이터베이스에서 사용자를 찾습니다.
        Optional<Member> member = memberRepository.findByEmail(email);

        // 사용자가 데이터베이스에 있는지 여부를 반환합니다.
        return member.isPresent();
    }
}
