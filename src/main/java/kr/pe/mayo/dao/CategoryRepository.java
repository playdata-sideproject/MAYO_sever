package kr.pe.mayo.dao;

import kr.pe.mayo.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
