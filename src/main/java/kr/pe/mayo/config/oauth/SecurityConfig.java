package kr.pe.mayo.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity  // 스프링 시큐리티 필터(SecurityConfig 클래스)가 스프링 필터 체인에 등록되게 하는 어노테이션
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)  // Secured 어노테이션을 활성화시키는 어노테이션
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity
                .authorizeRequests()
                    .antMatchers("/user/**").authenticated()   // /user로 들어오는 모든 요청은 권한 필요
                    .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")   // /admin으로 들어오는 모든 요청은 ADMIN이라는 역할이 있는 사람만 접근 가능 (스프링 시큐리티에서는 항상 ROLE_ADMIN 이런 형태여야 한다)
                    .anyRequest().permitAll()  // 그외 요청은 모두 접근 가능
                    .and()
                .logout()
                    .logoutSuccessUrl("/")
                    .and()
                .oauth2Login()
                    .loginPage("/")
                    .defaultSuccessUrl("/login-success")
                    .userInfoEndpoint()  // 구글로그인 완료된 후 - AccessToken+사용자프로필 정보를 받은 상태
                    .userService(principalOauth2UserService);  // 이 클래스에서 정의한대로 후처리 하겠다
        }

}
