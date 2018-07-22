package com.ilkaygunel.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.ilkaygunel.entities.Member;

@Service
public class MemberGetService extends BaseService {

	public List<Member> getAllMemberList() {
		Logger LOGGER = loggingUtil.getLoggerForMemberGetting(this.getClass());
		try {
			LOGGER.log(Level.INFO, "getAllMemberList() method is running.");
			// return (List<Member>) memberRepository.findAll();
			return memberFacade.findListByNamedQuery("Member.findAll");
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "An error occured while getting all members. Error is:" + ex.getMessage());
		}
		return null;
	}

	public Member getMemberViaId(long id) {
		Logger LOGGER = loggingUtil.getLoggerForMemberGetting(this.getClass());
		try {
			LOGGER.log(Level.INFO, "getMemberViaId(...) method is running.");
			// return memberRepository.findOne(id);
			return memberFacade.find(id);
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "An error occured while getting member via id. Error is:" + ex.getMessage());
		}
		return null;
	}

	public Member getMemberViaFirstName(String firstName) {
		Logger LOGGER = loggingUtil.getLoggerForMemberGetting(this.getClass());
		try {
			LOGGER.log(Level.INFO, "getMemberViaFirstName(...) method is running.");
			Map<Object, Object> parameterMap = new HashMap<>();
			parameterMap.put("firstName", firstName);
			return memberFacade.findListByNamedQuery("Member.findByFirstName", parameterMap).get(0);
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE,
					"An error occured while getting member via first name. Error is:" + ex.getMessage());
		}
		return null;
	}
}
