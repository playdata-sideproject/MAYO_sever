package kr.pe.mayo.dao;

import kr.pe.mayo.domain.Comments;
import org.springframework.data.repository.CrudRepository;

public interface CommentsRepository extends CrudRepository<Comments,Long> {

}
