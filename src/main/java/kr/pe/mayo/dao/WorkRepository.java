package kr.pe.mayo.dao;

import kr.pe.mayo.domain.User;
import kr.pe.mayo.domain.Work;
import org.springframework.data.repository.CrudRepository;

public interface WorkRepository extends CrudRepository<Work, Long> {
    Work findByUserIdxAndTitle(User userIdx, String title);
}
