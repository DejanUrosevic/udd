package ftn.upp.app.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ftn.upp.app.model.User;
import ftn.upp.app.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class LogInController {

	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<User> logIn(@RequestBody String payload){
		
		JSONObject json = new JSONObject(payload);
		
		User user = userService.logIn(json.getString("username"), json.getString("password"));
		
		if(user != null){
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		
		return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(value = "/singup", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<User> singUp(@RequestBody String payload){
		
		JSONObject json = new JSONObject(payload);
		
		User user = new User();
		
		user.setFirstname(json.getString("firstname"));
		user.setLastname(json.getString("lastname"));
		user.setPassword(json.getString("password"));
		user.setUsername(json.getString("username"));
		
		boolean userSave = userService.singUp(user);
		
		if(userSave){
			return new ResponseEntity<User>(HttpStatus.OK);
		}
		
		return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
	}
 }
