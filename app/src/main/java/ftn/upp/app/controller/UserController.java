package ftn.upp.app.controller;

import java.util.Date;
import java.util.List;

import javax.jws.soap.SOAPBinding.Use;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ftn.upp.app.model.User;
import ftn.upp.app.model.UserType;
import ftn.upp.app.service.CategoryService;
import ftn.upp.app.service.UserService;
import ftn.upp.app.service.UserTypeService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	UserTypeService utService;
	
	@Autowired
	CategoryService categoryService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<LoginResponse> logIn(@RequestBody String payload){
		
		JSONObject json = new JSONObject(payload);
		
		if(json.getString("username").equals("") || json.getString("username").equals(null) 
				|| json.getString("password").equals("") || json.getString("password").equals(null)){
			return new ResponseEntity<LoginResponse>(HttpStatus.UNAUTHORIZED);
		}
			
		
		User user = userService.logIn(json.getString("username"), json.getString("password"));
		
		if(user != null){
			String token = Jwts.builder().setSubject(user.getUsername()).claim("roles", user.getType().getName())
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
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<User> getUser(final HttpServletRequest req) throws ServletException{

		final HttpServletRequest request = (HttpServletRequest) req;

		final String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ") || authHeader.equals("Bearer null") || authHeader.endsWith("null")) {
			throw new ServletException("Missing or invalid Authorization header.");
		}

		String token = authHeader.substring(7); // The part after "Bearer "

		Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();

		String username = (String) claims.get("sub");
		
		User user = userService.findByUsername(username);
		
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<User> updateUser(@RequestBody String payload){
		System.out.println(payload);
		
		JSONObject json = new JSONObject(payload);
		
		User user = userService.findByUsername(json.getString("username"));

		user.setFirstname(json.getString("firstname"));
		user.setLastname(json.getString("lastname"));
		
		String newPass = "";
		String repeatPass = "";
		
		try {
			newPass = json.getString("newPass");
			repeatPass = json.getString("repeatPass");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		if(!newPass.equals("") && !repeatPass.equals("")){
			if(newPass.equals(repeatPass)){
				user.setPassword(newPass);
			}
		}
			
		User u = userService.saveOrUpdate(user);
		
		return new ResponseEntity<User>(u, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getOneUser(@PathVariable("id") Integer id){
		return new ResponseEntity<User>(userService.findOne(id), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAll(){
		return new ResponseEntity<List<User>>(userService.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/types", method = RequestMethod.GET)
	public ResponseEntity<List<UserType>> getAllType(){
		return new ResponseEntity<List<UserType>>(utService.findAll(), HttpStatus.OK);
	}
	

	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<User> save(@RequestBody String payload){
		
		JSONObject json = new JSONObject(payload);
		
		if(userService.findByUsername(json.getString("username")) != null){
			return new ResponseEntity<User>(HttpStatus.FORBIDDEN);
		}
		
		User user = new User();
		
		user.setFirstname(json.getString("firstname"));
		user.setLastname(json.getString("lastname"));
		user.setPassword(json.getString("password"));
		user.setUsername(json.getString("username"));
		
		try {
			int type = json.getInt("type");
			user.setType(utService.findOne(type));
		} catch (Exception e) {
			user.setType(null);
		}
		
		try {
			int cat = json.getInt("category");
			user.setCategory(categoryService.findOne(cat));
		} catch (Exception e) {
			user.setCategory(null);
		}
		
		
		User u = userService.saveOrUpdate(user);
		
		return new ResponseEntity<User>(u, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<User> update(@RequestBody String payload){
		
		JSONObject json = new JSONObject(payload);
		
		User user = userService.findOne(json.getInt("id"));
		
		user.setFirstname(json.getString("firstname"));
		user.setLastname(json.getString("lastname"));
		user.setPassword(json.getString("password"));
		user.setUsername(json.getString("username"));
		
		try {
			int type = json.getInt("type");
			user.setType(utService.findOne(type));
		} catch (Exception e) {
			user.setType(null);
		}
		
		try {
			int cat = json.getInt("category");
			user.setCategory(categoryService.findOne(cat));
		} catch (Exception e) {
			user.setCategory(null);
		}
		
		
		User u = userService.saveOrUpdate(user);
		
		return new ResponseEntity<User>(u, HttpStatus.OK);
	}
	
	@SuppressWarnings("unused")
    private static class LoginResponse {
        public String token;

        public LoginResponse(final String token) {
            this.token = token;
        }
    }
 }
