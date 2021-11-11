package kr.pe.mayo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    @ManyToOne
    private Category catIdx;
    private String workTitle;
    private String workContent;
    private int imgIdx;
    private char workStatus;
}
