package kr.pe.mayo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class WorkImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workImgIdx;

    private Long workIdx;
    private String originalFileName;
    private String storedFilePath;
    private long fileSize;
}
