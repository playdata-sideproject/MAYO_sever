package kr.pe.mayo.domain;

import kr.pe.mayo.domain.dto.Category;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    @ManyToOne
    @JoinColumn(name="userIdx")
    private User userIdx;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String title;

    private String content;

    @CreationTimestamp
    private Timestamp createdAt;

    private String status;

    @OneToMany
    @JoinColumn(name="Idx")
    private List<WorkImg> workImgs;
}