package it.hysen.springmvc.controller.api.v1;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import it.hysen.springmvc.controller.api.CRUDRestController;
import it.hysen.springmvc.model.AbstractEntity;
import it.hysen.springmvc.service.GenericService;

public abstract class AbstractCRUDRestController<E extends AbstractEntity<K>, K extends Serializable>
        extends AbstractV1APIController implements CRUDRestController<E, K> {
	
	private GenericService<E, K> genericService;

	public AbstractCRUDRestController(GenericService<E, K> genericService) {
		this.genericService = genericService;
	}
	
	@Override
	public ResponseEntity<Void> create(E entity) {
		if (entity.getId() != null && this.genericService.find(entity.getId()) != null) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		this.genericService.saveOrUpdate(entity);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<E> delete(K key) {
		E entity = this.genericService.find(key);
		if (entity == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		this.genericService.remove(entity);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@Override
	public ResponseEntity<Void> deleteAll() {
		this.genericService.removeAll();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@Override
	public ResponseEntity<E> find(K key) {
		E entity = this.genericService.find(key);
		if (entity == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<List<E>> findAll() {
		List<E> list = this.genericService.findAll();
		if (list.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<E> update(K key, E entity) {
		E currentEntity = this.genericService.find(key);
		if (currentEntity == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		this.genericService.update(entity);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
}
