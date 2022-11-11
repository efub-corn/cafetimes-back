package com.efub.cafetimes.service;

import com.efub.cafetimes.domain.User;
import com.efub.cafetimes.domain.UserPrincipal;
import com.efub.cafetimes.dto.OAuthAttributesDto;
import com.efub.cafetimes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuthUserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    //서드파티에 사용자 정보를 요청할 수 있는 access token 을 얻고나서 실행되는 메소드
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // accessToken으로 서드파티에 요청해서 사용자 정보를 얻어옴
        Map<String, Object> oAuth2UserAttributes = super.loadUser(userRequest).getAttributes();

        //현재 진행 중인 서비스 구분 코드
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        //로그인 진행 시 키가 되는 pk값
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributesDto attributes = OAuthAttributesDto.of(registrationId, userNameAttributeName, oAuth2UserAttributes);

        User user = saveOrUpdateUser(attributes);

        //return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRole())), attributes.getAttributes(), attributes.getNameAttributeKey());
        return UserPrincipal.create(user, oAuth2UserAttributes);
    }

    private User saveOrUpdateUser(OAuthAttributesDto attributes){
        //회원가입 되어있지 않다면 가입 처리, 가입되어있다면 정보 업데이트&로그인 처리
        User user = userRepository.findUserByKakaoId(attributes.getKakaoId());

        if(user==null){
            //회원가입
            return userRepository.save(attributes.toEntity());
        } else {
            user.upadate(attributes.getNickname(), attributes.getImage());
            return user;
        }
    }

}
