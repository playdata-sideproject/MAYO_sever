package kr.pe.mayo.controller;

import kr.pe.mayo.domain.Wish;
import kr.pe.mayo.domain.dto.WishDTO;
import kr.pe.mayo.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class WishController {

    @Autowired
    private WishService service;

    //위시리스트 조회
    @GetMapping("/wish")
    public List<WishDTO> getMyWishList(HttpSession session) {
        List<WishDTO> wishList = service.getMyWishList(session);
        return wishList;
    }

    //위시리스트 삭제
    @DeleteMapping("/wish/{wishIdx}")
    public String deleteMyWish(@PathVariable long wishIdx) {
        service.deleteMyWish(wishIdx);
        return "삭제성공";
    }

    //위시리스트 등록
    //@PostMapping("/wish/{workIdx}")
    @GetMapping("/wish/add/{workIdx}")
    public String addWish(HttpSession session, @PathVariable long workIdx){
        service.addWish(session, workIdx);
        return "등록성공";
    }

}
