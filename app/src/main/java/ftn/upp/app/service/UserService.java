package ftn.upp.app.service;

import java.util.List;

import ftn.upp.app.model.User;

public interface UserService {

	public User logIn(String username, String password);
	
	public boolean singUp(User user);
	
	public User findByUsername(String username);
	
	public User saveOrUpdate(User user);
	
	public List<User> findAll();
	
	public User findOne(int id);
}
