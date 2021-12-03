package kr.pe.mayo.domain.dto;

import kr.pe.mayo.domain.Wish;
import kr.pe.mayo.domain.Work;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WishDTO {
    private long idx;
    private long workIdx;
    private long userIdx;

    public WishDTO(Wish entity) {
        this.idx = entity.getIdx();
        this.workIdx = entity.getWork().getIdx();
        this.userIdx = entity.getUser().getIdx();
    }
}
