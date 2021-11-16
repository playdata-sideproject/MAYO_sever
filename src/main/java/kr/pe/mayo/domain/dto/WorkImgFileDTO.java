package kr.pe.mayo.domain.dto;

import kr.pe.mayo.domain.WorkImgFile;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkImgFileDTO {
    private Long workIdx;
    private String originalFileName;
    private String storedFilePath;
    private long fileSize;

    public WorkImgFile toEntity() {
        return WorkImgFile.builder()
                .workIdx(workIdx)
                .originalFileName(originalFileName)
                .storedFilePath(storedFilePath)
                .fileSize(fileSize)
                .build();
    }
}
