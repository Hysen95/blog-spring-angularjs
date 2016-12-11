package it.hysen.springmvc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import it.hysen.springmvc.model.Role;

@Repository
public class RoleDAOImpl extends AbstractGenericDAO<Role, Integer> implements RoleDAO {

	@Override
	public Role findByParentId(Role entity) {
		List<Role> list = this.findAll();
		for (Role item : list) {
			if (entity.getParentId().equals(item.getId())) {
				return item;
			}
		}
		return null;
	}
	
}
