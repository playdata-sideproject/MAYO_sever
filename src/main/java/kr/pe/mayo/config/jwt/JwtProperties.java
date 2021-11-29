package kr.pe.mayo.config.jwt;

public interface JwtProperties {
    String SECRET = "mayo";
    int EXPIRATION_TIME = 864000000;  // 10일
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
