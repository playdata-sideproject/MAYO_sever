package kr.pe.mayo.config.oauth;

import kr.pe.mayo.domain.User;
import kr.pe.mayo.domain.dto.Role;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/** 시큐리티로 로그인하기
 시큐리티가 /login 주소를 낚아채서 로그인을 진행시킨다
 로그인 완료 후 시큐리티 세션에 해당 정보 넣어줘야 한다 (서버 세션 내에 시큐리티 세션 영역이 있음, Security ContextHolder라는 key값으로 시큐리티 세션이라는 것을 구분 ??? )

 - 시큐리티 세션에 들어갈 수 있는 건 Authentication 타입 객체임.
 - Authentication 객체 안에 user 정보를 저장할 때 저장할 수 있는 타입은
 1. 일반로그인 시 - UserDetails 타입 객체
 2. OAuth 로그인 시 - OAuth2User 타입 객체
 => 우리가 만든 PrincipalDetails 클래스가 UserDetails, OAuth2User 인터페이스를 상속하게 만들어야 한다!!!

 ===========================================================================

 즉, PrincipalDetails 클래스의 역할
 1. 우리가 만든 User 클래스형 객체를 품기
 2. Authentication 객체 안에 들어갈 수 있는 형태로 만들기
 */

@Data
public class PrincipalDetails implements OAuth2User {

    private User user;
    private Map<String, Object> attributes;  // oauth 로그인 완료후 super.loadUser(userRequest).getAttributes() 메소드를 통해 Map 객체 받아짐

    // oauth 로그인 시 사용되는 생성자
    public PrincipalDetails(User user, Map<String, Object> attributes){
        this.user = user;
        this.attributes = attributes;
    }

    // OAuth2User 인터페이스 메소드 오버라이딩

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // 밑에것들은 뭔지잘모르겠음
    // 해당 유저의 권한을 리턴하는 곳!!
    // ROLE_USER 리턴하는 역할 (아마도 후에 관리자인지 유저인지 확인할 때 필요한듯?)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole().toString();
            }
        });
//        user.getRole();
        return collect;
    }

    @Override
    public String getName() { 
        return user.getUserIdx()+"";
    }
}
