package kr.pe.mayo.config.oauth.provider;


import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo{

    private Map<String, Object> attributes; //oAuth2User.getAttributes()
    private Map<String, Object> kakaoAccount;
    private Map<String, Object> userInfo;

    public KakaoUserInfo(Map<String, Object> attributes) {

        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        String id = String.valueOf(attributes.get("id"));
        return (String) id;
    }

    @Override
    public String getName() {

        kakaoAccount = (Map)attributes.get("kakao_account");
        userInfo= (Map)kakaoAccount.get("profile");
        return (String) userInfo.get("nickname");
    }

    @Override
    public String getEmail() {
        kakaoAccount = (Map)attributes.get("kakao_account");
        return (String) kakaoAccount.get("email");
    }

    @Override
    public String getProvider() {

        return "kakao";
    }

}
