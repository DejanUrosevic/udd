package ftn.upp.app.service;

import java.util.List;

import ftn.upp.app.model.Category;

public interface CategoryService {

	public void saveOrUpdate(Category category, Long id);
	
	public List<Category> findAll();
	
	public Category findOne(Long id);
	
	public Category findByName(String name);
}
