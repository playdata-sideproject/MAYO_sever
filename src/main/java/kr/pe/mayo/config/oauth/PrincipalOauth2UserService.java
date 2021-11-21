package kr.pe.mayo.config.oauth;

import kr.pe.mayo.config.oauth.provider.GoogleUserInfo;
import kr.pe.mayo.config.oauth.provider.KakaoUserInfo;
import kr.pe.mayo.config.oauth.provider.NaverUserInfo;
import kr.pe.mayo.config.oauth.provider.OAuth2UserInfo;
import kr.pe.mayo.dao.UserRepository;
import kr.pe.mayo.domain.User;
import kr.pe.mayo.domain.dto.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private UserRepository dao;
    @Autowired
    private HttpSession session;

    // 구글로그인 후처리 메소드 - 구글로부터 받은 userRequest 를 후처리
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        // oauth 로그인 후 강제 회원가입 처리
        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("google");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            System.out.println("naver");
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        } else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")){
            System.out.println("kakao");
            // kakao_account안에 또 profile이라는 JSON객체가 있음.
            oAuth2UserInfo= new KakaoUserInfo((Map)oAuth2User.getAttributes());
        } else {
            System.out.println("지원하지 않습니다.");
        }

        // 아래 코드는 구글 OAuth2User2 한정적
        // 때문에 카카오일때도 이메일과 다른 정보를 담을 수 있게 서비스 객체 Refactoring 필요
        String provider = userRequest.getClientRegistration().getRegistrationId();  // = google
        String providerId = oAuth2UserInfo.getProviderId();  // = 구글이 제공해주는 회원 고유 id
        String name = oAuth2UserInfo.getName();
        String email = oAuth2UserInfo.getEmail();
        String username = provider + "-" + providerId;  // 절대 중복되지 않기 위해 이렇게 만들어줌
        Role role = Role.ROLE_USER;


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
            session.setAttribute("user", user);
        }

        return new PrincipalDetails(user, oAuth2User.getAttributes());   // 여기서 PrincipalDetails 객체를 리턴해주면 이게 Authentication 객체로 들어가서 시큐리티 세션에 저장됨됨
    }

}
