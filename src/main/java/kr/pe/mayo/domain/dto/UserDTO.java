package kr.pe.mayo.domain.dto;

import kr.pe.mayo.domain.User;
import kr.pe.mayo.domain.Work;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserDTO {

    @Data
    @AllArgsConstructor
    public static class Register{
        String name;
        String email;
        String phone;
        String birth;
        String school;
    }

    @Data
    @AllArgsConstructor
    public static class Update{
        String name;
        String email;
        String phone;
        String birth;
        String school;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class getInfo{
        String name;
        String email;
        String phone;
        String birth;
        String school;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class getBasicInfo{
        String name;
        String email;
        String phone;

    }

}
