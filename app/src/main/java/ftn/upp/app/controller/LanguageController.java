package ftn.upp.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ftn.upp.app.model.Language;
import ftn.upp.app.service.LanguageService;

@RestController
@RequestMapping(value = "/languages")
public class LanguageController {

	@Autowired
	LanguageService languageService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Language>> getLanguages(){
		return new ResponseEntity<List<Language>>(languageService.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Language> getLanguage(@PathVariable Integer id){
		return new ResponseEntity<Language>(languageService.findOne(id), HttpStatus.OK);
	}
	
}
