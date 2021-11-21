package kr.pe.mayo.domain.dto;

import kr.pe.mayo.domain.Category;
import kr.pe.mayo.domain.User;
import kr.pe.mayo.domain.Work;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

@Data
public class WorkDTO {
    private long catIdx;
    private String workTitle;
    private String workContent;
    private List<MultipartFile> workImages;

}
