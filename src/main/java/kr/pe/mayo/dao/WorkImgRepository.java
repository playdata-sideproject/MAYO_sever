package kr.pe.mayo.dao;

import kr.pe.mayo.domain.Work;
import kr.pe.mayo.domain.WorkImg;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WorkImgRepository extends CrudRepository<WorkImg, Long> {
    List<WorkImg> findByWorkIdx(long workIdx);
}
