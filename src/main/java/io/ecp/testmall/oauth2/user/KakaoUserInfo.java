package io.ecp.testmall.oauth2.user;

import java.util.Map;

public class KakaoUserInfo {

    public static String socialId;
    public static Map<String, Object> account;
    public static Map<String, Object> profile;

    public KakaoUserInfo(Map<String, Object> attributes) {
        socialId = String.valueOf(attributes.get("id"));
        account = (Map<String, Object>) attributes.get("kakao_account");
        profile = (Map<String, Object>) account.get("profile");
    }

    public String getSocialId() {
        return socialId;
    }

    public String getEmail() {
        return String.valueOf(account.get("email"));
    }
}
