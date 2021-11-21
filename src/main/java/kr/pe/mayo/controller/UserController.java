package kr.pe.mayo.controller;

import kr.pe.mayo.config.oauth.PrincipalDetails;
import kr.pe.mayo.dao.UserRepository;
import kr.pe.mayo.domain.User;
import kr.pe.mayo.domain.dto.UserDTO;
import kr.pe.mayo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/")
    public String index(Model model){

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

        model.addAttribute("urls", oauth2AuthenticationUrls);
        return "index";
    }

    @GetMapping("/login-success")
    public String check(@AuthenticationPrincipal PrincipalDetails principalDetails){

        User user = userRepository.findByUsername(principalDetails.getUser().getUsername());

        System.out.println(user);
        if(!user.isRegCompleted()) {
            return "register";
        }
        return "mypage";
    }

    @PostMapping("/register")
    public String register(UserDTO.Register register, @AuthenticationPrincipal PrincipalDetails principalDetails){
        User user = userService.registerCreator(principalDetails, register);

        // 수정된 유저 정보 시큐리티 세션에 다시 저장
        principalDetails.setUser(user);
        session.setAttribute("user", user.getIdx());

        return "mypage";
    }

    @GetMapping("/user/withdrawal")
    public @ResponseBody String userWithdrawal(@AuthenticationPrincipal PrincipalDetails principalDetails){

        User user = userRepository.findByUsername(principalDetails.getUser().getUsername());
        userService.userWithdrawal(user);
        return "탈퇴";
    }

    // 내 정보 조회
    @GetMapping("user/myInfo")
    public @ResponseBody UserDTO.getInfo myInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return userService.myInfo(principalDetails.getUser().getIdx());
    }

    // 전체조회
    @GetMapping("/getUserByName")
    public @ResponseBody List<UserDTO.getBasicInfo> myInfo(String name) {
        return userService.getyUserByName(name);
    }

    @GetMapping("user/myInfo/update")
    public @ResponseBody UserDTO.getInfo updateMyInfo(UserDTO.Update update, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        User user = userService.updateUser(principalDetails, update);
        principalDetails.setUser(user);

        return userService.myInfo(principalDetails.getUser().getIdx());
    }
}
