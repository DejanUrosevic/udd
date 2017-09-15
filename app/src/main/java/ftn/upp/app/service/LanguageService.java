package ftn.upp.app.service;

import java.util.List;

import ftn.upp.app.model.Language;

public interface LanguageService {

	public Language findOne(Integer id);
	
	public List<Language> findAll();
}
