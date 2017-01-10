package ftn.upp.app.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.upp.app.model.User;
import ftn.upp.app.model.UserType;
import ftn.upp.app.repository.UserRepository;
import ftn.upp.app.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public User logIn(String username, String password) {
		
		User user = userRepository.findByUsername(username);
		
		if(user != null){
			if(user.getPassword().equals(password)){
				return user;
			}
		}
		
		return null;
	}

	@Override
	public boolean singUp(User user) {
		
		User u = userRepository.findByUsername(user.getUsername());
		
		if(u == null){
			user.setType(UserType.POSETILAC);
			user.setCategory(null);
			
			userRepository.save(user);
			
			return true;
		} 
		
		return false;
		
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}
