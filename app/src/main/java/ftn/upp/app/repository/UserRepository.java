package ftn.upp.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ftn.upp.app.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{

	public User findByUsername(String username);
}
