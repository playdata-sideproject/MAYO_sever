package kr.pe.mayo.domain.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class WorkDTO {
    private long catIdx;
    private String workTitle;
    private String workContent;
    private List<MultipartFile> workImages;

}
