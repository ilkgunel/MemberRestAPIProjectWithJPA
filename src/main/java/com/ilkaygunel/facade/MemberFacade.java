package com.ilkaygunel.facade;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ilkaygunel.entities.Member;

@Component
public class MemberFacade extends AbstractFacade<Member> {

	@PersistenceContext
	private EntityManager entityManager;

	public MemberFacade() {
		super(Member.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	@Transactional
	public void deleteByNativeQuery(Object id) {
		Query deleteQuery = getEntityManager().createNativeQuery("DELETE FROM Member where id = " + id);
		deleteQuery.executeUpdate();
	}

}
