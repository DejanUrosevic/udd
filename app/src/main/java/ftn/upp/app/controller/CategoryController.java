package ftn.upp.app.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ftn.upp.app.model.Category;
import ftn.upp.app.model.User;
import ftn.upp.app.service.CategoryService;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {

	@Autowired
	CategoryService categoryService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Category>> getCategories(){
		List<Category> categories = categoryService.findAll();
		
		return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<List<Category>> save(@RequestBody String payload){
		
		JSONObject json = new JSONObject(payload);
		
		Category c = new Category();
		
		c.setName(json.getString("name"));
		
		categoryService.saveOrUpdate(c, null);
		
		List<Category> categories = categoryService.findAll();
		
		return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Category> getCategory(@PathVariable("id") Integer id){
		
		return new ResponseEntity<Category>(categoryService.findOne(id), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<List<Category>> updateCategory(@RequestBody String payload){
		
		JSONObject json = new JSONObject(payload);
		
		Category c = new Category();
		c.setName(json.getString("name"));
		
		categoryService.saveOrUpdate(c, json.getInt("id"));
		
		return new ResponseEntity<List<Category>>(categoryService.findAll(), HttpStatus.OK);
	}
}
