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
    private Work workIdx;
    private long userIdx;
    private String commTitle;
    private String commContent;
    @CreationTimestamp
    private Timestamp commCreatedAt;

}
