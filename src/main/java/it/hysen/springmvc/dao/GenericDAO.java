package it.hysen.springmvc.dao;

import java.io.Serializable;
import java.util.List;

import it.hysen.springmvc.model.AbstractEntity;

public interface GenericDAO<E extends AbstractEntity<K>, K extends Serializable> {
	
	void add(E entity);
	
	E find(K key);

	List<E> findAll();
	
	void remove(E entity);
	
	int removeAll();
	
	void saveOrUpdate(E entity);
	
	void update(E entity);

}
