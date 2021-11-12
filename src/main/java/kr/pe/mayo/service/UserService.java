package kr.pe.mayo.service;

import kr.pe.mayo.config.oauth.PrincipalDetails;
import kr.pe.mayo.dao.UserRepository;
import kr.pe.mayo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserService {

    @Autowired
    private UserRepository dao;


    public User checkFirstJoin(String username) {
        return dao.findByUsername(username);
    }

    /** 자동 회원가입 후 추가정보 입력 폼 */
    public User register(String phone, String birth, String school) {
        // 시큐리티 세션 호출
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getPrincipal());  // PrincipalDetails(user=kr.pe.mayo.domain.User@315d4798, attributes={sub=113751636601935164576, name=������, given_name=����, family_name=��, picture=https://lh3.googleusercontent.com/a/AATXAJxNCBozlh0bcRKvYMp6U4gt1MAW5TVMcPu52k9k=s96-c, email=hmjang28@gmail.com, email_verified=true, locale=ko})

        // 시큐리티 세션에 저장된 user 정보 불러오는 코드 - 사용자정의 어노테이션으로 만들면 좋을듯
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        User sessionUser = principalDetails.getUser();
        System.out.println(sessionUser.getUsername());

        User user = dao.findByUsername(sessionUser.getUsername());
        user.setPhone(phone);
        user.setBirth(birth);
        user.setSchool(school);
        dao.save(user);

        return user;
    }


}
