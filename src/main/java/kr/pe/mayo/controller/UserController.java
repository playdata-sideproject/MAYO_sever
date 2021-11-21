package kr.pe.mayo.controller;

import kr.pe.mayo.config.oauth.PrincipalDetails;
import kr.pe.mayo.dao.UserRepository;
import kr.pe.mayo.domain.User;
import kr.pe.mayo.domain.dto.UserDTO;
import kr.pe.mayo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class UserController {

    // OAuth기본 url
    private static final String authorizationRequestBaseUri = "oauth2/authorization";
    Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

    // ClientRegistrationRepository
    // 설정한 OAuth 클라이언트 프로퍼티로 ClientRegistration 을 가지고 있다
    @Autowired
    ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session;

    @GetMapping
    public PrincipalDetails getPrincipalDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println(principalDetails);

        return principalDetails;
    }

    @GetMapping("/")
    public String index(Model model){

        // application-oauth.properties 에 있는 OAuth2 클라이언드 정보 가져오기
        // 이 코드셋을 사용하는 이유는 View에서 하나하나 작성하는것보단 SNS 로그인이 추가될 때 마다 정보를 View 같이 보내주며
        // 반복문을 통해 렌더링

        /*
        application-oauth.properties 에 있는 OAuth2 클라이언드 정보 가져오기
        이 코드셋을 사용하는 이유는 View에서 직접 작성하지 않고 SNS 로그인이 추가될 때 마다 정보를 View로 전달하여 반복문을 통해 렌더링
         */

        // authorizationRequestBaseUri = "oauth2/authorization" + "/" + registration.getRegistrationId()
        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
                .as(Iterable.class);
        if (type != ResolvableType.NONE &&
                ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }
        assert clientRegistrations != null;
        clientRegistrations.forEach(registration ->
                oauth2AuthenticationUrls.put(registration.getClientName(),
                        authorizationRequestBaseUri + "/" + registration.getRegistrationId()));

        System.out.println(oauth2AuthenticationUrls);
        /*
        {
            Google=oauth2/authorization/google,
            kakao=oauth2/authorization/kakao,
            Naver=oauth2/authorization/naver,
        }
         */
        model.addAttribute("urls", oauth2AuthenticationUrls);
        return "index";
    }

    @GetMapping("/login-success")
    public String check(){
        PrincipalDetails principalDetails = getPrincipalDetails();

        User user = userRepository.findByUsername(principalDetails.getUser().getUsername());

        System.out.println(user);
        if(!user.isRegCompleted()) {
            return "register";
        }
        return "mypage";
    }

    @PostMapping("/register")
    public String register(UserDTO.Register register){
        PrincipalDetails principalDetails = getPrincipalDetails();
        User user = userService.registerCreator(principalDetails, register);

        // 수정된 유저 정보 시큐리티 세션에 다시 저장
        principalDetails.setUser(user);
        session.setAttribute("user", user.getUserIdx());

        return "mypage";
    }

    @GetMapping("/user/withdrawal")
    public @ResponseBody String userWithdrawal(){

        PrincipalDetails principalDetails = getPrincipalDetails();

        User user = userRepository.findByUsername(principalDetails.getUser().getUsername());
        userService.userWithdrawal(user);
        return "탈퇴";
    }

    // 내 정보 조회
    @GetMapping("user/myInfo")
    public @ResponseBody UserDTO.getInfo myInfo() {

        PrincipalDetails principalDetails = getPrincipalDetails();
        return userService.myInfo(principalDetails.getUser().getUserIdx());
    }

    // 전체조회
    @GetMapping("/getUserByName")
    public @ResponseBody List<UserDTO.getBasicInfo> myInfo(String name) {
        return userService.getyUserByName(name);
    }

    @GetMapping("user/myInfo/update")
    public @ResponseBody UserDTO.getInfo updateMyInfo(UserDTO.Update update) {

        PrincipalDetails principalDetails = getPrincipalDetails();
        User user = userService.updateUser(principalDetails, update);

        principalDetails.setUser(user);

        return userService.myInfo(principalDetails.getUser().getUserIdx());
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
//        System.out.println("register 회원가입 완료후: " + user.getSchool());
    }

}
