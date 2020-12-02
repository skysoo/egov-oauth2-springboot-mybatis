package com.study.egovspringbootmybatis.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Getter
public class OAuthAttributes {
    private final String id;
    private final Map<String, Object> attributes;
    private final String nameAttributeKey;
    private final String name;
    private final String email;
    private final String role;
    private final String provider;
    private final String picture;

    @Builder
    public OAuthAttributes(String id,
                           Map<String, Object> attributes,
                           String nameAttributeKey, String name,
                           String email,
                           String role, String provider, String picture) {
        this.id = id;
        this.attributes = attributes;
        this.nameAttributeKey= nameAttributeKey;
        this.name = name;
        this.role = role;
        this.email = email;
        this.provider = provider;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributeName,
                                     OAuth2User oAuth2User) {
        switch (registrationId){
            case "google":
                return ofGoogle(userNameAttributeName, oAuth2User);
            case "naver":
                return ofNaver(userNameAttributeName, oAuth2User);
            default:
                log.warn("##### It is not a provider that can be used in this application. => {}",registrationId);
                break;
        }
        return null;
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName,
                                            OAuth2User oAuth2User) {
        List<? extends GrantedAuthority> list = oAuth2User.getAuthorities().stream().collect(Collectors.toList());
        String role = list.get(0).getAuthority();
        String subRole = role.substring(role.indexOf("_") + 1);
        return OAuthAttributes.builder()
                .id(String.valueOf(oAuth2User.getAttributes().get("sub")))
                .name((String) oAuth2User.getAttributes().get("name"))
                .email((String) oAuth2User.getAttributes().get("email"))
                .role(subRole)
                .provider("google")
                .attributes(oAuth2User.getAttributes())
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName,
                                           OAuth2User oAuth2User) {

        Map<String, LinkedHashMap<String, String>> map = (Map<String, LinkedHashMap<String, String>>) oAuth2User.getAttributes().get(userNameAttributeName);
        List<? extends GrantedAuthority> list = oAuth2User.getAuthorities().stream().collect(Collectors.toList());
        String role = list.get(0).getAuthority();
        String subRole = role.substring(role.indexOf("_") + 1);
        return OAuthAttributes.builder()
                .id(String.valueOf(map.get("id")))
                .name(String.valueOf(map.get("name")))
                .email(String.valueOf(map.get("email")))
                .role(subRole)
                .provider("naver")
                .attributes(oAuth2User.getAttributes())
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

}
