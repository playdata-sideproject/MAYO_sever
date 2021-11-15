package kr.pe.mayo.domain.dto;

import kr.pe.mayo.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

public class WorkDTO {

    @Data
    @AllArgsConstructor
    public static class Upload {
        private String workTitle;
        private User user;
        @Enumerated(EnumType.STRING)
        private Category category;
        private String workContent;
        private List<WorkImgFileDTO> workImgs;
    }


}
