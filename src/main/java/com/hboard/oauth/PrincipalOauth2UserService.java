package com.hboard.oauth;

import com.hboard.user.SiteUser;
import com.hboard.user.UserRepository;
import com.hboard.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    // 구글로 부터 받은 userRequest 데이터에 대한 후처리를 하는 함수
    // 구글로그인 버튼 클릭 -> 구글 로그인 창 -> 로그인 완료 -> code를 리턴(OAuth-Client라이브러리) -> AccessToken요청
    // AccessToken 요청에 대한 응답이 -> userRequest 정보 -> loadUser() 호출-> 구글로부터 회원프로필을 받아준다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 회원가입을 강제로 진행
        String username = oAuth2User.getAttribute("email");
        String password = passwordEncoder.encode(oAuth2User.getAttribute("sub"));
        String email = oAuth2User.getAttribute("email");
        String role = UserRole.USER.getValue();

        SiteUser user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            user = new SiteUser(null, username, password, email, role);
            userRepository.save(user);
        }

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }


}
