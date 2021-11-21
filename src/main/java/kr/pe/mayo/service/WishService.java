package kr.pe.mayo.service;

import kr.pe.mayo.dao.WishRepository;
import kr.pe.mayo.dao.WorkRepository;
import kr.pe.mayo.domain.User;
import kr.pe.mayo.domain.Wish;
import kr.pe.mayo.domain.Work;
import kr.pe.mayo.domain.dto.WishDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class WishService {

    @Autowired
    private WishRepository wishDAO;

    @Autowired
    private WorkRepository workDAO;

    //위시리스트 조회
    public List<WishDTO> getMyWishList(User user){
        List<Wish> wish = wishDAO.findByUser(user);
        List<WishDTO> wishList = new ArrayList<WishDTO>();

        if(wish == null){
            System.out.println("위시리스트가 비어있습니다.");
        } else{
            wish.forEach(v -> wishList.add(new WishDTO(v)));
        }
        return wishList;
    }
    
    //위시리스트 삭제
    public void deleteMyWish(long wishIdx){
        Wish wish = wishDAO.findById(wishIdx).get();

        if(wish == null){
            System.out.println("찾는 위시가 없습니다.");
        } else{
            wishDAO.deleteById(wishIdx);
        }
    }

    //위시리스트 등록
    public void addWish(User user, long workIdx){
        Work work = workDAO.findById(workIdx).get();
        System.out.println(user);
        wishDAO.save(new Wish(work, user));
    }
}
