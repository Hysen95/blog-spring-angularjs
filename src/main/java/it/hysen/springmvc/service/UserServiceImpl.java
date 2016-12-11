package it.hysen.springmvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.hysen.springmvc.dao.GenericDAO;
import it.hysen.springmvc.dao.UserDAO;
import it.hysen.springmvc.model.User;

@Service("userService")
public class UserServiceImpl extends AbstractGenericService<User, Integer> implements UserService {

	@Autowired
	private UserDAO dao;

	public UserServiceImpl(@Qualifier("userDAOImpl") GenericDAO<User, Integer> genericDAO) {
		super(genericDAO);
		this.dao = (UserDAO) genericDAO;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public User authenticate(User entity) {
		return this.dao.findByUsernameAndPassword(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public String hashPassword(String password) {
		return this.dao.hashPassword(password);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean isUserExist(User user) {
		return this.dao.find(user.getId()) != null || this.dao.findByUsername(user) != null
		        || this.dao.findByEmail(user) != null;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void softDelete(User entity) {
		this.dao.softDelete(entity);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void softDeleteAll() {
		this.dao.softDeleteAll();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void softReactive(User entity) {
		this.dao.softReactive(entity);
	}
	
}
