package it.hysen.springmvc.controller.api.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.hysen.springmvc.model.Role;
import it.hysen.springmvc.service.GenericService;
import it.hysen.springmvc.service.RoleService;

@RestController
public class RoleRestController extends AbstractCRUDRestController<Role, Integer> {

	private RoleService roleService;

	public RoleRestController(@Qualifier("roleService") GenericService<Role, Integer> genericService) {
		super(genericService);
		this.roleService = (RoleService) genericService;
	}

	@Override
	@RequestMapping(value = "/role/", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Role entity) {
		if (entity.getParentId() == null || this.roleService.isParentRoleExist(entity)) {
			return super.create(entity);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@Override
	@RequestMapping(value = "/role/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Role> delete(@PathVariable("id") Integer key) {
		return super.delete(key);
	}

	@Override
	@RequestMapping(value = "/role/", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteAll() {
		return super.deleteAll();
	}

	@Override
	@RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
	public ResponseEntity<Role> find(@PathVariable("id") Integer key) {
		return super.find(key);
	}

	@Override
	@RequestMapping(value = "/role/", method = RequestMethod.GET)
	public ResponseEntity<List<Role>> findAll() {
		return super.findAll();
	}

	@Override
	@RequestMapping(value = "/role/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Role> update(@PathVariable("id") Integer key, @RequestBody Role entity) {
		if (entity.getParentId() == null || this.roleService.isParentRoleExist(entity)) {
			return super.update(key, entity);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}