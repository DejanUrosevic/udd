package ftn.upp.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ftn.upp.app.model.Language;

@Repository
public interface LanguageRespository extends CrudRepository<Language, Integer>{

	public Language findByName(String name);
}
