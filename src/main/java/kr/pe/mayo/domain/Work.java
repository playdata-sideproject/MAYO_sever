package kr.pe.mayo.domain;

import kr.pe.mayo.domain.dto.Category;
import kr.pe.mayo.domain.dto.WorkImgFileDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long workIdx;
    @ManyToOne
    private User userIdx;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String workTitle;
    private String workContent;
    private char workStatus;

    @ManyToOne
    private List<WorkImgFile> workImgFiles;


}
