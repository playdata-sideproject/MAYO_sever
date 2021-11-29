package kr.pe.mayo.domain;


import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class WorkImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    private long workIdx;
    private String originalFileName;
    private String storedFilePath;
    private long fileSize;
}