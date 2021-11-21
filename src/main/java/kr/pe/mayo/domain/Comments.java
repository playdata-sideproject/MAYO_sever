package kr.pe.mayo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commIdx;
    @ManyToOne
    @JoinColumn(name="workIdx")
    private Work workIdx;
    //하나의 유저가 여러개의 작품에 댓글 작성 가능
    //한개의 작품이 여러개의 댓글 가질 수 있음.
    //한개의 작품에 한명의 유저가 여러 댓글 작성 가능.
    private long userIdx;
    private String commTitle;
    private String commContent;
    @CreationTimestamp
    private Timestamp commCreatedAt;
}
