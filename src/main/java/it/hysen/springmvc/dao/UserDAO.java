package it.hysen.springmvc.dao;

import it.hysen.springmvc.model.User;

public interface UserDAO extends GenericDAO<User, Integer>, SoftOperationsDAO<User, Integer> {
	
	User findByEmail(User entity);
	
	User findByUsername(User entity);
	
	User findByUsernameAndPassword(User entity);

	String hashPassword(String password);

}
