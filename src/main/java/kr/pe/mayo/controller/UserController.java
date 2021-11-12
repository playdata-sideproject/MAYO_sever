package kr.pe.mayo.controller;

import kr.pe.mayo.config.oauth.PrincipalDetails;
import kr.pe.mayo.dao.UserRepository;
import kr.pe.mayo.domain.User;
import kr.pe.mayo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login-success")
    public String check(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println(principalDetails);

        User user = userRepository.findByUsername(principalDetails.getUser().getUsername());

        if(user.getPhone() == null) {
            return "redirect:register.html";
        } else {
            return "redirect:index.html";
        }
        // 뷰 분기
        // HttpSession에 넣나?
    }


    @PostMapping("/register")
    public String register(HttpSession session, String phone, String birth, String school){
        User user = userService.registerCreator(phone, birth, school);
        session.setAttribute("user", user);

        return "redirect:index.html";
    }

    @GetMapping("/user/oauth-test")
    public void sessionTest(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("oauth 회원가입 완료후: " + principalDetails);
        System.out.println("oauth 회원가입 완료후: " + principalDetails.getUser());
        System.out.println("oauth 회원가입 완료후: " + principalDetails.getAttributes());
    }

    @GetMapping("/user/register-test")
    public void sessionTest2(HttpSession session){
        User user = (User) session.getAttribute("user");
        System.out.println("register 회원가입 완료후: " + user.getSchool());
    }
}
