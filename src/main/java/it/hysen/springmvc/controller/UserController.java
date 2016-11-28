package it.hysen.springmvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.hysen.springmvc.model.User;
import it.hysen.springmvc.service.UserService;

@Controller
@RequestMapping(value = "/user")
public class UserController extends AbstractController {

	@Autowired
	private UserService service;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String authenticate() {
		return "partials/user/login";
	}
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<User> authenticate(HttpServletRequest request, @RequestBody User model) {
		User foundUser = this.service.authenticate(model);
		if (foundUser != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", model);
			return new ResponseEntity<>(foundUser, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String getHome() {
		return "partials/user/index";
	}
	
	@RequestMapping(value = "/authenticated", method = RequestMethod.POST)
	public ResponseEntity<User> getUserAuthenticated(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			if (session.getAttribute("user") != null) {
				return new ResponseEntity<>((User) session.getAttribute("user"), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public ResponseEntity<User> logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			if (session.getAttribute("user") != null) {
				session.setAttribute("user", null);
				return new ResponseEntity<>(HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
}
