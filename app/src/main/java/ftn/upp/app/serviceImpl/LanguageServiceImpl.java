package ftn.upp.app.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.upp.app.model.Language;
import ftn.upp.app.repository.LanguageRespository;
import ftn.upp.app.service.LanguageService;

@Service
public class LanguageServiceImpl implements LanguageService{

	@Autowired
	LanguageRespository languageRepository;
	
	@Override
	public Language findOne(Integer id) {
		return languageRepository.findOne(id);
	}

	@Override
	public List<Language> findAll() {
		return (List<Language>) languageRepository.findAll();
	}

	@Override
	public Language findByName(String name) {
		return languageRepository.findByName(name);
	}

}
