package it.hysen.springmvc.controller.api;

import java.io.Serializable;

import org.springframework.http.ResponseEntity;

import it.hysen.springmvc.model.AbstractEntity;

public interface SoftOperationsRestController<E extends AbstractEntity<K>, K extends Serializable> {
	
	ResponseEntity<E> softDelete(K key);
	
	ResponseEntity<E> softReactive(K key);
	
}
