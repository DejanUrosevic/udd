package ftn.upp.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ftn.upp.app.model.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer>{

}
