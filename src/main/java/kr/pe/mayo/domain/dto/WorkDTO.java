package kr.pe.mayo.domain.dto;

import kr.pe.mayo.domain.User;
import kr.pe.mayo.domain.Work;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.util.List;

public class WorkDTO {

    @Data
    @AllArgsConstructor
    public static class Upload {
        private String workTitle;
        private User user;
        private String username;
        @Enumerated(EnumType.STRING)
        private Category category;
        private String workContent;

        public Work toEntity() {
            return Work.builder().workTitle(workTitle).userIdx(user).category(category).workContent(workContent).build();
        }
    }

}
