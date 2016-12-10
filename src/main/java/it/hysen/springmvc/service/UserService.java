package it.hysen.springmvc.service;

import it.hysen.springmvc.model.User;

public interface UserService extends GenericService<User, Integer>, SoftOperationsService<User, Integer> {
	
	User authenticate(User entity);

	String hashPassword(String password);

	boolean isUserExist(User entity);

}
