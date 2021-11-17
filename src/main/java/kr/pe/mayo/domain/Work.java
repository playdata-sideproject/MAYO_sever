package kr.pe.mayo.domain;

import kr.pe.mayo.domain.dto.Category;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@ToString
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long workIdx;

    // 칼럼명을 따로 설정을 안하면 "user_idx_user_idx"로 지정됨 (맘에 안듬..!)
    // DDL에서 지정한 user_idx와 자동적으로 연동이 안됨..!
    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User userIdx;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String workTitle;
//    // 등록 하고 이미지 엔터티와 연결을 위해, work_idx를 찾아올 idx 외 다른 고유값이 필요함
//    // 그걸 등록한 user의 username으로 하는게 좋다고 생각해서 일단 이렇게 했습니다
//    // 근데 이렇게 하면 User객체와 연결이 필요없지 않을까 생각도 드네용~
//    private String username;
    private String workContent;

    @CreationTimestamp
    private Timestamp WorkCreatedAt;

    private char workStatus;

    @OneToMany
    private List<WorkImg> workImgs;
}
