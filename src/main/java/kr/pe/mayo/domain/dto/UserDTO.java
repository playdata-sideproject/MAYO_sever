package kr.pe.mayo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public class UserDTO {

    @Data
    @AllArgsConstructor
    public static class Register{
        String name;
        String phone;
        String birth;
        String school;
    }

}
