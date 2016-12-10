package it.hysen.springmvc.controller.api.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.hysen.springmvc.controller.api.SoftOperationsRestController;
import it.hysen.springmvc.model.User;
import it.hysen.springmvc.service.GenericService;
import it.hysen.springmvc.service.UserService;

@RestController
public class UserRestController extends AbstractCRUDRestController<User, Integer>
        implements SoftOperationsRestController<User, Integer> {
	
	@Autowired
	private UserService service;
	
	public UserRestController(@Qualifier("userService") GenericService<User, Integer> genericService) {
		super(genericService);
		this.service = (UserService) genericService;
	}

	@Override
	@RequestMapping(value = "/user/", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody User entity) {
		entity.setPassword(this.service.hashPassword(entity.getPassword()));
		return super.create(entity);
	}

	@Override
	@RequestMapping(value = "/user/{key}", method = RequestMethod.DELETE)
	public ResponseEntity<User> delete(@PathVariable("key") Integer key) {
		return super.delete(key);
	}

	@Override
	@RequestMapping(value = "/user/", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteAll() {
		return super.deleteAll();
	}

	@Override
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> find(@PathVariable("id") Integer key) {
		return super.find(key);
	}
	
	@Override
	@RequestMapping(value = "/user/", method = RequestMethod.GET)
	public ResponseEntity<List<User>> findAll() {
		return super.findAll();
	}

	@Override
	@RequestMapping(value = "/user/soft/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<User> softDelete(@PathVariable("id") Integer key) {
		User user = this.service.find(key);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		this.service.softDelete(user);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Override
	@RequestMapping(value = "/user/soft/{id}", method = RequestMethod.PUT)
	public ResponseEntity<User> softReactive(@PathVariable("id") Integer key) {
		User user = this.service.find(key);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		this.service.softReactive(user);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Override
	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	public ResponseEntity<User> update(@PathVariable("id") Integer key, @RequestBody User entity) {
		return super.update(key, entity);
	}

}