package com.ilkaygunel.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

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

}
