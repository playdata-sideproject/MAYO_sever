package kr.pe.mayo.config.oauth;

import kr.pe.mayo.controller.UserController;
import kr.pe.mayo.dao.UserRepository;
import kr.pe.mayo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository dao;
    @Autowired
    private UserController controller;


    // 구글로그인 후처리 메소드 - 구글로부터 받은 userRequest 를 후처리
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("getAttributes:  " + oAuth2User.getAttributes());  // System.out.println(userRequest.getClientRegistration().getClientId());


        // oauth 로그인 후 강제 회원가입 처리
        String provider = userRequest.getClientRegistration().getClientId();  // = google
        String providerId = oAuth2User.getAttribute("sub");  // = 구글이 제공해주는 회원 고유 id
        String name = oAuth2User.getAttribute("name");
        String email = oAuth2User.getAttribute("email");
        String username = provider + "-" + providerId;  // 절대 중복되지 않기 위해 이렇게 만들어줌
        String role = "ROLE_USER";

        User user = dao.findByUsername(username);
        if (user == null){   // db에 회원정보 없다면 새로운 user객체 생성
            // id는 autoincrement 되니까 id 컬럼 빼고 build
            user = User.builder()
                    .username(username)
                    .name(name)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            
            dao.save(user);
        }

        return new PrincipalDetails(user, oAuth2User.getAttributes());   // 여기서 PrincipalDetails 객체를 리턴해주면 이게 Authentication 객체로 들어가서 시큐리티 세션에 저장됨됨
    }

}
