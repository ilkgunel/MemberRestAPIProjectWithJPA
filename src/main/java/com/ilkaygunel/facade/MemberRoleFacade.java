package com.ilkaygunel.facade;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

import com.ilkaygunel.entities.MemberRoles;

@Component
public class MemberRoleFacade extends AbstractFacade<MemberRoles> {

	@PersistenceContext
	private EntityManager entityManager;

	public MemberRoleFacade() {
		super(MemberRoles.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

}
