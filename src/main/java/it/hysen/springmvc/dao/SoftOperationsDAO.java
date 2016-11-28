package it.hysen.springmvc.dao;

import java.io.Serializable;

import it.hysen.springmvc.model.AbstractEntity;

public interface SoftOperationsDAO<E extends AbstractEntity<K>, K extends Serializable> {
	
	void softDelete(E entity);
	
	void softDeleteAll();

	void softReactive(E entity);

}
