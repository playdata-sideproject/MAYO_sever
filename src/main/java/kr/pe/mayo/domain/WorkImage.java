package kr.pe.mayo.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class WorkImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long workImgIdx;
    @ManyToOne
    @JoinColumn(name="workIdx")
    private Work workIdx;
    private String origFileName;
    private String filePath;
    private long fileSize;

    @Builder
    public WorkImage(Work workIdx, String origFileName, String filePath, long fileSize) {
        this.workIdx = workIdx;
        this.origFileName = origFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }
}
