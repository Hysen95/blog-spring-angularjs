package it.hysen.springmvc.dao;

import it.hysen.springmvc.model.Role;

public interface RoleDAO extends GenericDAO<Role, Integer> {

	Role findByParentId(Role entity);
	
}
