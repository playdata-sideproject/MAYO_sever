package kr.pe.mayo.dao;

import kr.pe.mayo.domain.User;
import kr.pe.mayo.domain.Wish;
import kr.pe.mayo.domain.Work;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WishRepository extends CrudRepository<Wish, Long> {
    List<Wish> findByUser(User user);
    Wish findBywishIdx(long workIdx);

}
