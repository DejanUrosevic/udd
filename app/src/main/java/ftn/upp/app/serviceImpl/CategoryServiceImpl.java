package ftn.upp.app.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.upp.app.model.Category;
import ftn.upp.app.repository.CategoryRepository;
import ftn.upp.app.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public void saveOrUpdate(Category category, Integer id) {
		
		if(id == null){
			categoryRepository.save(category);
		} else {
			Category c = categoryRepository.findOne(id);
			
			c.setName(category.getName());
			
			categoryRepository.save(c);
		}
	}

	@Override
	public List<Category> findAll() {
		return (List<Category>) categoryRepository.findAll();
	}

	@Override
	public Category findOne(Integer id) {
		
		return categoryRepository.findOne(id);
	}
}
