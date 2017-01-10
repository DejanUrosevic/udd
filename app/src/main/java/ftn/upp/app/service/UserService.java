package ftn.upp.app.service;

import ftn.upp.app.model.User;

public interface UserService {

	public User logIn(String username, String password);
	
	public boolean singUp(User user);
	
	public User findByUsername(String username);
}
