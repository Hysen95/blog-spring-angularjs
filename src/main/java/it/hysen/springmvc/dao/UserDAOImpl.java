package it.hysen.springmvc.dao;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import it.hysen.springmvc.model.User;

@Repository
public class UserDAOImpl extends GenericDAOImpl<User, Long> implements UserDAO {

	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public User findByEmail(User entity) {
		List<User> result = this.findAll();
		for (User item : result) {
			if (item.getEmail().equalsIgnoreCase(entity.getEmail())) {
				return item;
			}
		}
		return null;
	}
	
	@Override
	public User findByUsername(User entity) {
		List<User> result = this.findAll();
		for (User item : result) {
			if (item.getUsername().equalsIgnoreCase(entity.getUsername())) {
				return item;
			}
		}
		return null;
	}
	
	@Override
	public User findByUsernameAndPassword(User entity) {
		List<User> result = this.findAll();
		for (User item : result) {
			if (item.getDeletedAt() == null) {
				if (item.getUsername().equalsIgnoreCase(entity.getUsername())
				        && this.encoder.matches(entity.getPassword(), item.getPassword())) {
					return item;
				}
			}
		}
		return null;
	}
	
	@Override
	public String hashPassword(String password) {
		return this.encoder.encode(password);
	}
	
	@Override
	public void softReactive(User entity) {
		entity.setDeletedAt(null);
		this.update(entity);
	}
	
	@Override
	public void softDelete(User entity) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		entity.setDeletedAt(Timestamp.valueOf(dateFormat.format(date)));
		this.update(entity);
	}
	
	@Override
	public void softDeleteAll() {
		List<User> result = this.findAll();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		Timestamp currentTimestamp = Timestamp.valueOf(dateFormat.format(date));
		for (User item : result) {
			item.setDeletedAt(currentTimestamp);
			this.update(item);
		}
	}
	
}
