package kr.pe.mayo.domain.dto;

import kr.pe.mayo.domain.Work;
import kr.pe.mayo.domain.WorkImage;
import lombok.Data;

@Data
public class WorkImageDTO {
    private Work workIdx;
    private String origFileName;
    private String filePath;
    private long fileSize;
}
