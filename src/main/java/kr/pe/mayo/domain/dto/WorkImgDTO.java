package kr.pe.mayo.domain.dto;

import kr.pe.mayo.domain.Work;
import kr.pe.mayo.domain.WorkImg;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkImgDTO {
    private long workIdx;
    private String originalFileName;
    private String storedFilePath;
    private long fileSize;

    public WorkImg toEntity() {
        return WorkImg.builder()
                .workIdx(workIdx)
                .originalFileName(originalFileName)
                .storedFilePath(storedFilePath)
                .fileSize(fileSize)
                .build();
    }
}
