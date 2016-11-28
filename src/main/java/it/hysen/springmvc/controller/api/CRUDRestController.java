package it.hysen.springmvc.controller.api;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.ResponseEntity;

import it.hysen.springmvc.model.AbstractEntity;

public interface CRUDRestController<E extends AbstractEntity<K>, K extends Serializable> {
	
	ResponseEntity<Void> create(E entity);
	
	ResponseEntity<E> delete(K key);
	
	ResponseEntity<Void> deleteAll();
	
	ResponseEntity<E> find(K key);
	
	ResponseEntity<List<E>> findAll();
	
	ResponseEntity<E> update(K key, E entity);
	
}
