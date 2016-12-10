package it.hysen.springmvc.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.hysen.springmvc.dao.GenericDAO;
import it.hysen.springmvc.model.AbstractEntity;

@Service
public abstract class AbstractGenericService<E extends AbstractEntity<K>, K extends Serializable>
        implements GenericService<E, K> {
	
	private GenericDAO<E, K> genericDAO;
	
	public AbstractGenericService() {
	}
	
	public AbstractGenericService(GenericDAO<E, K> genericDAO) {
		this.genericDAO = genericDAO;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(E entity) {
		this.genericDAO.add(entity);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public E find(K key) {
		return this.genericDAO.find(key);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<E> findAll() {
		return this.genericDAO.findAll();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void remove(E entity) {
		this.genericDAO.remove(entity);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public int removeAll() {
		return this.genericDAO.removeAll();
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveOrUpdate(E entity) {
		this.genericDAO.saveOrUpdate(entity);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(E entity) {
		this.genericDAO.update(entity);
	}
	
}
