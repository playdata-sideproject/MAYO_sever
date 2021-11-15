package kr.pe.mayo.domain;

import kr.pe.mayo.domain.dto.Category;
import kr.pe.mayo.domain.dto.WorkImgFileDTO;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
public class WorkImgFile {
    @Id
    private Long workImg_idx;
    private String originalFileName;
    private String storedFilePath;
    private long fileSize;
}
