package it.hysen.springmvc.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.hysen.springmvc.model.AbstractEntity;

@SuppressWarnings("unchecked")
@Repository
public abstract class AbstractGenericDAO<E extends AbstractEntity<K>, K extends Serializable> implements GenericDAO<E, K> {
	
	private Class<? extends E> classType;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public AbstractGenericDAO() {
		Type type = this.getClass().getGenericSuperclass();
		ParameterizedType parameterizedType = (ParameterizedType) type;
		this.classType = (Class<E>) parameterizedType.getActualTypeArguments()[0];
	}
	
	@Override
	public void add(E entity) {
		this.getCurrentSession().save(entity);
	}
	
	@Override
	public E find(K key) {
		return this.getCurrentSession().get(this.classType, key);
	}
	
	@Override
	public List<E> findAll() {
		return this.getCurrentSession().createCriteria(this.classType).list();
	}
	
	public Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}
	
	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}
	
	@Override
	public void remove(E entity) {
		this.getCurrentSession().delete(entity);
	}
	
	@Override
	public int removeAll() {
		return this.getCurrentSession().createQuery("DELETE FROM " + this.classType.getSimpleName()).executeUpdate();
	}
	
	@Override
	public void saveOrUpdate(E entity) {
		this.getCurrentSession().saveOrUpdate(entity);
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void update(E entity) {
		this.getCurrentSession().saveOrUpdate(entity);
	}
	
}
