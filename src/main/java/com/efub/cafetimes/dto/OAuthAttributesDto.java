package com.efub.cafetimes.dto;

import com.efub.cafetimes.config.Authority;
import com.efub.cafetimes.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
public class OAuthAttributesDto {
    private Map<String, Object> attributes;
    private String registrationId; //서비스 구분
    private String nameAttributeKey;
    private String nickname;
    private String email;
    private String image;

    @Builder
    public OAuthAttributesDto(Map<String, Object> attributes, String registrationId, String nameAttributeKey, String nickname, String email, String image){
        this.attributes = attributes;
        this.registrationId = registrationId;
        this.nameAttributeKey = nameAttributeKey;
        this.nickname = nickname;
        this.email = email;
        this.image = image;
    }

    public static OAuthAttributesDto of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        switch (registrationId) {
            case "kakao":
                return ofKakao(registrationId, userNameAttributeName, attributes);
            default:
                throw new RuntimeException();
        }
    }

    public static OAuthAttributesDto ofKakao(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        // email
        Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
        // nickname, profile_image
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");

        return OAuthAttributesDto.builder()
                .registrationId(registrationId)
                .nameAttributeKey(userNameAttributeName)
                .nickname((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .image((String) kakaoProfile.get("profile_image_url"))
                .attributes(attributes)
                .build();
    }

    public User toEntity() {
        User user = User.builder()
                .attributeKey(nameAttributeKey)
                .nickname(nickname)
                .image(image)
                .email(email)
                .authority(Authority.CUSTOMER)
                .build();

        return user;
    }

}
