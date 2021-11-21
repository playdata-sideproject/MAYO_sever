package kr.pe.mayo.dao;

import kr.pe.mayo.domain.Wish;
import kr.pe.mayo.domain.WorkImg;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WorkImgRepository extends CrudRepository<WorkImg, Long> {
}
