package ftn.upp.app.controller;

import java.util.Date;

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
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping(value = "/user")
public class LogInController {

	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<LoginResponse> logIn(@RequestBody String payload){
		
		JSONObject json = new JSONObject(payload);
		
		if(json.getString("username").equals("") || json.getString("username").equals(null) 
				|| json.getString("password").equals("") || json.getString("password").equals(null)){
			return new ResponseEntity<LoginResponse>(HttpStatus.UNAUTHORIZED);
		}
			
		
		User user = userService.logIn(json.getString("username"), json.getString("password"));
		
		if(user != null){
			String token = Jwts.builder().setSubject(user.getUsername()).claim("roles", user.getType())
					.setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "secretkey").compact();
			return new ResponseEntity<LoginResponse>(new LoginResponse(token), HttpStatus.OK);
		}
		
		return new ResponseEntity<LoginResponse>(HttpStatus.UNAUTHORIZED);
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
	
	@SuppressWarnings("unused")
    private static class LoginResponse {
        public String token;

        public LoginResponse(final String token) {
            this.token = token;
        }
    }
 }
