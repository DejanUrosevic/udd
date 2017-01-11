package ftn.upp.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ftn.upp.app.model.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer>{

}
