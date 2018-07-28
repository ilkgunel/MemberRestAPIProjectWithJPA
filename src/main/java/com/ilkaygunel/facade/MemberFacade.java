package com.ilkaygunel.facade;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

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

}
