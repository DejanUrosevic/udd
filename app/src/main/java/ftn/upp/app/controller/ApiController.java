package ftn.upp.app.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ftn.upp.app.model.Category;
import ftn.upp.app.model.UserType;
import ftn.upp.app.service.UserService;

@RestController
@RequestMapping(value = "/api")
public class ApiController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/role", method = RequestMethod.GET)
	public RoleUsername checkRola(final HttpServletRequest req) throws ServletException {
		
		final HttpServletRequest request = (HttpServletRequest) req;

		final String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ") || authHeader.equals("Bearer null") || authHeader.endsWith("null")) {
			throw new ServletException("Missing or invalid Authorization header.");
		}

		String token = authHeader.substring(7); // The part after "Bearer "

		Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();

		String rola = (String) claims.get("roles");
		
		if(rola.equals("administrator")){
			return new RoleUsername("admin");
		} else if (rola.equals("preplatnik")){
			return new RoleUsername("preplatnik");
		} else if (rola.equals("posetilac")){
			return new RoleUsername("posetilac");
		}

		return new RoleUsername();
	}
	
	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public Category checkUserCategory(final HttpServletRequest req) throws ServletException {
		
		final HttpServletRequest request = (HttpServletRequest) req;

		final String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ") || authHeader.equals("Bearer null") || authHeader.endsWith("null")) {
			throw new ServletException("Missing or invalid Authorization header.");
		}

		String token = authHeader.substring(7); // The part after "Bearer "

		Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();

		int userId = (int) claims.get("userId");
		
		return userService.findOne(userId).getCategory();
	}
	
	@SuppressWarnings("unused")
	private static class RoleUsername {
		public String role;

		public RoleUsername(final String role) {
			this.role = role;
		}

		public RoleUsername() {
			
		}
	}
}
