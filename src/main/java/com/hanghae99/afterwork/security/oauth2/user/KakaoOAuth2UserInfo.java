package com.hanghae99.afterwork.security.oauth2.user;

import java.util.LinkedHashMap;
import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return ((Integer) attributes.get("id")).toString();
    }

    @Override
    public String getName() {
        LinkedHashMap<String, Object> profile = (LinkedHashMap<String, Object>) attributes.get("properties");
        return (String) profile.get("nickname");
    }

    @Override
    public String getEmail() {
        LinkedHashMap<String, Object> kakaoAccount = (LinkedHashMap<String, Object>) attributes.get("kakao_account");
        return (String) kakaoAccount.get("email");
    }

    @Override
    public String getImageUrl() {
        LinkedHashMap<String, Object> profile = (LinkedHashMap<String, Object>) attributes.get("properties");
        return (String) profile.get("thumbnail_image");
    }
}
