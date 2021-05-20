package com.hanghae99.afterwork.security.oauth2.user;

import com.hanghae99.afterwork.entity.AuthProvider;
import com.hanghae99.afterwork.exception.OAuth2AuthenticationProcessingException;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if(registrationId.equalsIgnoreCase(AuthProvider.naver.toString())) {
            return new NaverOAuth2UserInfo((Map<String, Object>) attributes.get("response"));
        } else if(registrationId.equalsIgnoreCase(AuthProvider.kakao.toString())) {
            return new KakaoOAuth2UserInfo(attributes);
        }
        else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
