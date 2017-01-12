package ftn.upp.app.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.upp.app.model.User;
import ftn.upp.app.model.UserType;
import ftn.upp.app.repository.UserRepository;
import ftn.upp.app.service.UserService;
import ftn.upp.app.service.UserTypeService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserTypeService userTypeService;
	
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
		UserType ut = userTypeService.findOne(3);
		
		if(u == null){
			user.setType(ut);
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

	@Override
	public User saveOrUpdate(User user) {
		
		User u = userRepository.findByUsername(user.getUsername());
		
		if(u == null){
			userRepository.save(user);
			return user;
		} else {
			u.setCategory(user.getCategory());
			u.setFirstname(user.getFirstname());
			u.setLastname(user.getLastname());
			u.setPassword(user.getPassword());
			u.setType(user.getType());
			
			userRepository.save(u);
		}
		
		return u;
	}

	@Override
	public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	public User findOne(int id) {
		return userRepository.findOne(id);
	}

	

}
