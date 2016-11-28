package it.hysen.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.hysen.springmvc.dao.GenericDAO;
import it.hysen.springmvc.dao.RoleDAO;
import it.hysen.springmvc.model.Role;

@Service("roleService")
public class RoleServiceImpl extends GenericServiceImpl<Role, Long> implements RoleService {

	@Autowired
	private RoleDAO dao;
	
	public RoleServiceImpl(@Qualifier("roleDAOImpl") GenericDAO<Role, Long> genericDAO) {
		super(genericDAO);
		this.dao = (RoleDAO) genericDAO;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean isRoleExist(Role entity) {
		return this.dao.find(entity.getId()) != null;
	}
	
}
