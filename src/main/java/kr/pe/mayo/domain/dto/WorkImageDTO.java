package kr.pe.mayo.domain.dto;

import kr.pe.mayo.domain.WorkImage;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WorkImageDTO {
    private long imgIdx;
    private String origFileName;
    private String fileName;
    private String filePath;

    @Builder
    public WorkImageDTO(String origFileName, String fileName, String filePath) {
        this.origFileName = origFileName;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public WorkImage toEntity(){
        WorkImage build = WorkImage.builder()
                .origFileName(origFileName)
                .fileName(fileName)
                .filePath(filePath)
                .build();
        return build;
    }
}
