package kr.pe.mayo.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    @JoinColumn(name="work_idx")
    @ManyToOne
    private Work work;

    @JoinColumn(name="user_idx")
    @ManyToOne
    private User user;

    public Wish(Work work, User user) {
        this.work = work;
        this.user = user;
    }

}
