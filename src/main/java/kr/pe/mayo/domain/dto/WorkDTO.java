package kr.pe.mayo.domain.dto;

import kr.pe.mayo.domain.User;
import kr.pe.mayo.domain.Work;
import kr.pe.mayo.domain.WorkImg;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

public class WorkDTO {

    @Data
    public static class Get {
        private long workIdx;
        private String writer;
        private String category;
        private String workTitle;
        private String workContent;
        private Timestamp workCreatedAt;
        private char workStatus;
        private long thumbnailId;

        public Get(Work entity){
            this.workIdx = entity.getWorkIdx();
            this.writer = entity.getUserIdx().getUsername();
            this.category = entity.getCategory().getCategory();
            this.workTitle = entity.getWorkTitle();
            this.workContent = entity.getWorkContent();
            this.workCreatedAt = entity.getWorkCreatedAt();
            this.workStatus = entity.getWorkStatus();
            if (!entity.getWorkImgs().isEmpty()){  // 게시글에 포함된 이미지 있으면
                this.thumbnailId = entity.getWorkImgs().get(0).getWorkImgIdx();
            } else {  // 이미지 없으면 기본썸네일 반환
                this.thumbnailId = 0L;
            }
        }
    }

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
            return Work.builder()
                    .workTitle(workTitle)
                    .userIdx(user)
                    .category(category)
                    .workContent(workContent)
                    .build();
        }
    }

}
