package it.hysen.springmvc.service;

import java.io.Serializable;

import it.hysen.springmvc.model.AbstractEntity;

public interface SoftOperationsService<E extends AbstractEntity<K>, K extends Serializable> {

	void softDelete(E entity);

	void softDeleteAll();
	
	void softReactive(E entity);
	
}
