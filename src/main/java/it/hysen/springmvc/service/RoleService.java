package it.hysen.springmvc.service;

import it.hysen.springmvc.model.Role;

public interface RoleService extends GenericService<Role, Long> {

	boolean isRoleExist(Role entity);

}
