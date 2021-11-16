package kr.pe.mayo.dao;

import kr.pe.mayo.domain.WorkImage;
import kr.pe.mayo.domain.dto.WorkImageDTO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WorkImageRepository extends CrudRepository<WorkImage, Long> {
}
