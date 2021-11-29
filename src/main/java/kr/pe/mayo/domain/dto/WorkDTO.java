package kr.pe.mayo.domain.dto;

import kr.pe.mayo.domain.User;
import kr.pe.mayo.domain.Work;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Timestamp;

public class WorkDTO {

    @Data
    public static class List {
        private long idx;
        private String writer;
        private String category;
        private String title;
        private String content;
        private Timestamp createdAt;
        private String status;
        private long thumbnailId;

        public List(Work entity){
            this.idx = entity.getIdx();
            this.writer = entity.getUserIdx().getName();
            this.category = entity.getCategory().getCategory();
            this.title = entity.getTitle();
            this.content = entity.getContent();
            this.createdAt = entity.getCreatedAt();
            this.status = entity.getStatus();
            if (!entity.getWorkImgs().isEmpty()){  // 게시글에 포함된 이미지 있으면
                this.thumbnailId = entity.getWorkImgs().get(0).getIdx();
            } else {  // 이미지 없으면 기본썸네일 반환
                this.thumbnailId = 0L;
            }
        }
    }

    @Data
    @Builder
    public static class Upload {
        private String title;
        private User user;
        @Enumerated(EnumType.STRING)
        private Category category;
        private String content;

        public Work toEntity() {
            return Work.builder()
                    .title(title)
                    .userIdx(user)
                    .category(category)
                    .content(content)
                    .build();
        }
    }

}