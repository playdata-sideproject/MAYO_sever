package kr.pe.mayo.domain.dto;

import kr.pe.mayo.domain.WorkImg;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkImgDTO {
    private Long workIdx;
    private String originalFileName;
    private String storedFilePath;
    private Long fileSize;

    public WorkImg toEntity() {
        return WorkImg.builder()
                .workIdx(workIdx)
                .originalFileName(originalFileName)
                .storedFilePath(storedFilePath)
                .fileSize(fileSize)
                .build();
    }

    public WorkImgDTO(WorkImg workImg) {
        this.workIdx = workImg.getWorkIdx();
        this.originalFileName = workImg.getOriginalFileName();
        this.storedFilePath = workImg.getStoredFilePath();
        this.fileSize = workImg.getFileSize();
    }
}
