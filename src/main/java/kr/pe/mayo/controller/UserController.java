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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

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
    private HttpSession session;


    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String index(Model model){

        // application-oauth.properties 에 있는 OAuth2 클라이언드 정보 가져오기
        // 이 코드셋을 사용하는 이유는 View에서 하나하나 작성하는것보단 SNS 로그인이 추가될 때 마다 정보를 View 같이 보내주며
        // 반복문을 통해 렌더링

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

        // aplication
        System.out.println(oauth2AuthenticationUrls);
        /*
        {
            Google=oauth2/authorization/google,
            kakao=oauth2/authorization/kakao,
            Naver=oauth2/authorization/naver,
            Facebook=oauth2/authorization/facebook
        }
         */
        model.addAttribute("urls", oauth2AuthenticationUrls);

        return "index";
    }

    @GetMapping("/login-success")
//    public String checkFirstJoin(@AuthenticationPrincipal PrincipalDetails userDetails){
//
//        // 뷰 분기
//        User user = userService.checkFirstJoin(userDetails.getUser().getUsername());
//        String phone = user.getPhone();
//        if (phone == null){  // 처음 회원가입하는 사용자라면 ... 근데 이걸 phone 컬럼으로 검증하는게 논리에 맞나...? 무튼 추가정보 입력 폼으로 이동
//            return "register";
//        }
//
//        // 다시 방문해서 로그인하는 사용자라면 이미 추가정보도 다 있음. HttpSession에 저장
//        session.setAttribute("user", user);
//        return "redirect:/";

    public String check(Model model){

        String url = "/";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println(principalDetails);

        User user = userRepository.findByUsername(principalDetails.getUser().getUsername());

        System.out.println(user);
        if(user.getPhone() == null) {
            model.addAttribute("user", user.getName());
            url = "register.html";
            return url;
        }

        return index(model);
    }


    @PostMapping("/register")
    public String register(String name, String phone, String birth, String school){
        // DTO객체를 만든 이유:
        // - 후에 RestController & axios로 클라이언트와 요청응답을 할 때 더 깔끔한 코드구조를 위해..!
        UserDTO.Register register = new UserDTO.Register(name, phone, birth, school);
        User user = userService.registerCreator(register);
//        session.setAttribute("user", user);

        return "redirect:index.html";
    }

}
