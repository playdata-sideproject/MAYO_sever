package kr.pe.mayo.dao;

import kr.pe.mayo.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
    List<User> findByName(String name);
}
