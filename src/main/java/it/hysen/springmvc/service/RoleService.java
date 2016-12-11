package it.hysen.springmvc.service;

import it.hysen.springmvc.model.Role;

public interface RoleService extends GenericService<Role, Integer> {

	boolean isParentRoleExist(Role entity);

	boolean isRoleExist(Role entity);

}
