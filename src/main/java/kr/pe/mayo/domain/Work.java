package kr.pe.mayo.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

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
    @ManyToOne
    @JoinColumn(name="catIdx")
    private Category catIdx;
    private String workTitle;
    private String workContent;
    @CreationTimestamp
    private Timestamp workCreatedAt;
    private char workStatus;
}
