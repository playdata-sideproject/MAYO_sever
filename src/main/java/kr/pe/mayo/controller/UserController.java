package kr.pe.mayo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.json.JSONObject;
import kr.pe.mayo.config.oauth.PrincipalDetails;
import kr.pe.mayo.dao.UserRepository;
import kr.pe.mayo.domain.User;
import kr.pe.mayo.domain.dto.UserDTO;
import kr.pe.mayo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/")
//    public String index(Model model){

    public @ResponseBody Map<String, String> index(Model model){

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

////        System.out.println(oauth2AuthenticationUrls);
////
//        model.addAttribute("urls", oauth2AuthenticationUrls);
//        return "index";

        return oauth2AuthenticationUrls;
    }

    @GetMapping("/login-success")
    public String check(Model model, HttpServletResponse response, @AuthenticationPrincipal PrincipalDetails principalDetails) throws IOException {

        String CLIENT_URL = "http://localhost:3000/";

        User user = userRepository.findByUsername(principalDetails.getUser().getUsername());

        System.out.println(user.isRegCompleted());
        model.addAttribute("user", user);
        if(user.isRegCompleted()) {
            CLIENT_URL += "myPage";
        } else {
//            CLIENT_URL += principalDetails;
            CLIENT_URL += "register";
        }
        // 정보 완료시엔
        response.sendRedirect(CLIENT_URL);

        return "hello";
    }

    @PostMapping("/register")
    public String register(UserDTO.Register register, @AuthenticationPrincipal PrincipalDetails principalDetails){
        User user = userService.registerCreator(principalDetails, register);

        // 수정된 유저 정보 시큐리티 세션에 다시 저장
        principalDetails.setUser(user);

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

    @CrossOrigin
    @GetMapping("/test")
    public @ResponseBody JSONObject getAccessToken(String code) throws Exception {

        String accessToken = "";

        //HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //Http 객체(Header+Body) 만들기
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id","a9e33b9a3a3244f4f42462b3fb4981fb");
        params.add("redirect_uri","http://localhost:3000/oauth/callback/kakao");
        params.add("code", code);
        params.add("client_secret", "KxLJpvxXQTQHMfgyLXTBT0BZYFXqDymf");

        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, headers);


        //POST방식으로 key-value 데이터를 요청(카카오쪽으로)
        RestTemplate restTemplate = new RestTemplate(); //http 요청을 간단하게 해줄 수 있는 클래스

        ResponseEntity<JSONObject> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                JSONObject.class
        );

        System.out.println(response);

//        HttpEntity<MultiValueMap<String, Object>> restRequest = new HttpEntity<>(parameters, headers);
//        ResponseEntity<JSONObject> apiResponse = restTemplate.postForEntity(uri, restRequest, JSONObject.class);

        JSONObject responseBody = response.getBody();
//
        accessToken = (String) responseBody.get("access_token");
//
        return responseBody;
    }
}
