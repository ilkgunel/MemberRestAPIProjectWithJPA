package com.ilkaygunel.facade;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractFacade<T> {

	private Class<T> entityClass;

	public AbstractFacade(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	protected abstract EntityManager getEntityManager();

	@Transactional
	public T find(Object id) {
		return getEntityManager().find(entityClass, id);
	}

	@Transactional
	public List<T> findListByNamedQuery(String namedQuery) throws Exception {
		Query query = getEntityManager().createNamedQuery(namedQuery);
		return query.getResultList();
	}

	@Transactional
	public List<T> findListByNamedQuery(String namedQuery, Map<Object, Object> parameters) {
		Query query = getEntityManager().createNamedQuery(namedQuery);
		parameters.forEach((k, v) -> query.setParameter(k.toString(), v));
		return query.getResultList();
	}

	@Transactional
	public void create(T entity) {
		getEntityManager().persist(entity);
	}

	@Transactional
	public void update(T entity) {
		getEntityManager().merge(entity);
	}

}
