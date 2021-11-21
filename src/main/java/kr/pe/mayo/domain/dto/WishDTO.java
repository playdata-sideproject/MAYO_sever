package kr.pe.mayo.domain.dto;

import kr.pe.mayo.domain.Wish;
import kr.pe.mayo.domain.Work;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WishDTO {
    private long wishIdx;
    private long workIdx;
    private long userIdx;

    public WishDTO(Wish entity) {
        this.wishIdx = entity.getWishIdx();
        this.workIdx = entity.getWork().getWorkIdx();
        this.userIdx = entity.getUser().getUserIdx();
    }

}
