package kr.pe.mayo.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import kr.pe.mayo.config.jwt.JwtProperties;
import kr.pe.mayo.config.oauth.provider.GoogleUserInfo;
import kr.pe.mayo.config.oauth.provider.OAuth2UserInfo;
import kr.pe.mayo.dao.UserRepository;
import kr.pe.mayo.domain.User;
import kr.pe.mayo.domain.dto.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class JwtCreateController {

    private final UserRepository userRepository;

    @PostMapping("/oauth/jwt/google")
    public String jwtCreate(@RequestBody Map<String, Object> loginData){
        System.out.println(loginData.get("profileObj"));

        // 프론트에서 받아온 loginData 정보를 가지고 OAuth2에서 제공하는 googleUser 객체를 만든다
        OAuth2UserInfo googleUser = new GoogleUserInfo((Map<String, Object>)loginData.get("profileObj"));

        User user = userRepository.findByUsername(googleUser.getProvider()+"-"+googleUser.getProviderId());
        // 회원가입한적 없는 새로운 유저라면
        if (user==null){
            User newUser = User.builder()
                    .username(googleUser.getProvider()+"-"+googleUser.getProviderId())
                    .email(googleUser.getEmail())
                    .provider(googleUser.getProvider())
                    .providerId(googleUser.getProviderId())
                    .role(Role.ROLE_USER)
                    .build();
            user = userRepository.save(newUser);

        }
        // jwt토큰 생성
        String jwtToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+ JwtProperties.EXPIRATION_TIME))
                .withClaim("id", user.getIdx())
                .withClaim("username", user.getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        return jwtToken;
    }
}
