package com.ilkaygunel.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ilkaygunel.entities.Member;
import com.ilkaygunel.exception.CustomException;
import com.ilkaygunel.exception.ErrorCodes;

@Service
public class ActivateMemberService extends BaseService {

	public String activateMember(String activationToken) {
		try {
			Map<Object, Object> parameterMap = new HashMap();
			parameterMap.put("activationToken", activationToken);
			Member existingMember = memberFacade.findListByNamedQuery("Member.findByActivationToken", parameterMap)
					.get(0);
			if (existingMember == null) {
				throw new CustomException(ErrorCodes.ERROR_08.getErrorCode(),
						environment.getProperty(ErrorCodes.ERROR_08.getErrorCode()));
			}
			if (LocalDateTime.now().isAfter(existingMember.getActivationTokenExpDate())) {
				throw new CustomException(ErrorCodes.ERROR_09.getErrorCode(),
						environment.getProperty(ErrorCodes.ERROR_09.getErrorCode()));
			}
			existingMember.setEnabled(true);
			memberFacade.update(existingMember);
			return "Activating member is successfull";
		} catch (CustomException e) {
			return "An error occured while activating member:" + e.getErrorCode() + " " + e.getErrorMessage();
		} catch (Exception e) {
			return "An error occured while activating member:" + e.getMessage();
		}
	}
}
