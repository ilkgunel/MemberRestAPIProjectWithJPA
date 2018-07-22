package com.ilkaygunel.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ilkaygunel.entities.MemberRoles;

@Service
public class MemberRoleSaveService extends BaseService {

	public MemberRoles getMemberRoleWithEmail(String email) {
		Map<Object, Object> parameterMap = new HashMap();
		parameterMap.put("email", "email");
		return memberRoleFacade.findListByNamedQuery("MemberRoles.findByEmail", parameterMap).get(0);
	}
}
