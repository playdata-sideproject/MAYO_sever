package kr.pe.mayo.domain;

import kr.pe.mayo.domain.dto.Category;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long workIdx;

    @ManyToOne
    @JoinColumn(name="userIdx")
    private User userIdx;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String workTitle;

    private String workContent;

    @CreationTimestamp
    private Timestamp WorkCreatedAt;

    private char workStatus;

    @OneToMany
    private List<WorkImg> workImgs;
}
