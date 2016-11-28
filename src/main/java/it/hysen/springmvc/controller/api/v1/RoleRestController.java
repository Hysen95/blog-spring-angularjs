package it.hysen.springmvc.controller.api.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.hysen.springmvc.model.Role;
import it.hysen.springmvc.service.GenericService;

@RestController
public class RoleRestController extends AbstractCRUDRestController<Role, Long> {

	public RoleRestController(@Qualifier("roleService") GenericService<Role, Long> genericService) {
		super(genericService);
	}

	@Override
	@RequestMapping(value = "/role/", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Role entity) {
		return super.create(entity);
	}

	@Override
	@RequestMapping(value = "/role/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Role> delete(@PathVariable("id") Long key) {
		return super.delete(key);
	}

	@Override
	@RequestMapping(value = "/role/", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteAll() {
		return super.deleteAll();
	}

	@Override
	@RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
	public ResponseEntity<Role> find(@PathVariable("id") Long key) {
		return super.find(key);
	}

	@Override
	@RequestMapping(value = "/role/", method = RequestMethod.GET)
	public ResponseEntity<List<Role>> findAll() {
		return super.findAll();
	}

	@Override
	@RequestMapping(value = "/role/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Role> update(@PathVariable("id") Long key, @RequestBody Role entity) {
		return super.update(key, entity);
	}

}