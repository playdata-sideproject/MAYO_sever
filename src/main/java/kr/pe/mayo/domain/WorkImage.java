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
    private long imgIdx;
    private String origFileName;
    private String fileName;
    private String filePath;

    @Builder
    public WorkImage(String origFileName, String fileName, String filePath) {
        this.origFileName = origFileName;
        this.fileName = fileName;
        this.filePath = filePath;
    }
}
