package com.ilkaygunel.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.ilkaygunel.application.ResourceBundleMessageManager;
import com.ilkaygunel.constants.ConstantFields;
import com.ilkaygunel.entities.Member;
import com.ilkaygunel.entities.MemberRoles;
import com.ilkaygunel.exception.CustomException;
import com.ilkaygunel.exception.ErrorCodes;
import com.ilkaygunel.facade.MemberFacade;
import com.ilkaygunel.pojo.MemberOperationPojo;
import com.ilkaygunel.service.MemberRoleSaveService;
import com.ilkaygunel.wrapper.MemberIdWrapp;

@Component
public class MemberUtil {

	@Autowired
	private MemberFacade memberFacade;

	@Autowired
	private MemberRoleSaveService memberRoleSaveService;

	@Autowired
	private ResourceBundleMessageManager resourceBundleMessageManager;

	public boolean isValidEmailAddress(String emailAddress) {
		Pattern emailPattern = Pattern.compile(ConstantFields.EMAIL_CHECK_PATTERN.getConstantField());
		Matcher emailMatcher = emailPattern.matcher(emailAddress);
		return emailMatcher.matches();
	}

	public MemberOperationPojo checkEmailAddressAndLanguage(Member member, Logger LOGGER) {
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		Map<Object, Object> parameterMap = new HashMap<Object, Object>();
		parameterMap.put("email", member.getEmail());
		try {
			if (ObjectUtils.isEmpty(member.getEmail())) {
				throw new CustomException(ErrorCodes.ERROR_05.getErrorCode(),
						resourceBundleMessageManager.getValueOfProperty(ErrorCodes.ERROR_05.getErrorCode(), "en"));
			} else if (memberFacade.findListByNamedQuery("Member.findByEmail", parameterMap).size() > 0) {
				throw new CustomException(ErrorCodes.ERROR_06.getErrorCode(),
						resourceBundleMessageManager.getValueOfProperty(ErrorCodes.ERROR_06.getErrorCode(), "en") + " "
								+ member.getEmail());
			} else if (!isValidEmailAddress(member.getEmail())) {
				throw new CustomException(ErrorCodes.ERROR_07.getErrorCode(),
						resourceBundleMessageManager.getValueOfProperty(ErrorCodes.ERROR_07.getErrorCode(), "en") + " "
								+ member.getEmail());
			} else if (ObjectUtils.isEmpty(member.getMemberLanguageCode())) {
				throw new CustomException(ErrorCodes.ERROR_11.getErrorCode(),
						resourceBundleMessageManager.getValueOfProperty(ErrorCodes.ERROR_11.getErrorCode(), "en"));
			}
		} catch (CustomException customException) {
			memberOperationPojo.setErrorCode(customException.getErrorCode());
			memberOperationPojo.setResult(customException.getErrorMessage());
		}
		return memberOperationPojo;
	}

	public MemberOperationPojo checkMemberExistenceOnMemberList(List<MemberIdWrapp> memberIdList, String roleForCheck) {
		MemberOperationPojo memberOperationPojo = new MemberOperationPojo();
		memberOperationPojo.setMemberList(new ArrayList<Member>());
		try {
			for (MemberIdWrapp memberIdWrapp : memberIdList) {
				memberOperationPojo.getMemberList().add(checkMember(memberIdWrapp.getId(), roleForCheck));
			}
		} catch (CustomException customException) {
			memberOperationPojo.setErrorCode(customException.getErrorCode());
			memberOperationPojo.setResult(customException.getErrorMessage());
		} catch (Exception exception) {
			memberOperationPojo.setErrorCode(ErrorCodes.ERROR_10.getErrorCode());
			memberOperationPojo.setResult(exception.getMessage());
		}
		return memberOperationPojo;
	}

	public Member checkMember(Long memberId, String roleForCheck) throws Exception {
		Member member = memberFacade.find(memberId);

		if (member == null) {
			if (ConstantFields.ROLE_USER.getConstantField().equals(roleForCheck)) {
				throw new CustomException(ErrorCodes.ERROR_01.getErrorCode(),
						resourceBundleMessageManager.getValueOfProperty(ErrorCodes.ERROR_01.getErrorCode(), "en"));
			} else {
				throw new CustomException(ErrorCodes.ERROR_02.getErrorCode(),
						resourceBundleMessageManager.getValueOfProperty(ErrorCodes.ERROR_02.getErrorCode(), "en"));
			}
		} else {
			MemberRoles memberRoles = memberRoleSaveService.getMemberRoleWithEmail(member.getEmail());
			if (ConstantFields.ROLE_USER.getConstantField().equals(roleForCheck)
					&& !ConstantFields.ROLE_USER.getConstantField().equals(memberRoles.getRole())) {
				throw new CustomException(ErrorCodes.ERROR_03.getErrorCode(), resourceBundleMessageManager
						.getValueOfProperty(ErrorCodes.ERROR_03.getErrorCode(), member.getMemberLanguageCode()));
			} else if (ConstantFields.ROLE_ADMIN.getConstantField().equals(roleForCheck)
					&& !ConstantFields.ROLE_ADMIN.getConstantField().equals(memberRoles.getRole())) {
				throw new CustomException(ErrorCodes.ERROR_04.getErrorCode(), resourceBundleMessageManager
						.getValueOfProperty(ErrorCodes.ERROR_04.getErrorCode(), member.getMemberLanguageCode()));
			}
		}

		return member;
	}

	public void checkEmailAddressAndLanguageOnMemberList(List<Member> memberList, Logger LOGGER)
			throws CustomException {
		MemberOperationPojo memberOperationPojo = null;
		for (Member member : memberList) {
			memberOperationPojo = checkEmailAddressAndLanguage(member, LOGGER);
			if (!ObjectUtils.isEmpty(memberOperationPojo.getErrorCode())) {
				throw new CustomException(memberOperationPojo.getErrorCode(), memberOperationPojo.getResult());
			}
		}
	}

	public boolean isRequestContainsPassword(Member memberForUpdate) {
		return ObjectUtils.isEmpty(memberForUpdate.getPassword());
	}

	public MemberOperationPojo checkMemberListContainPassowrdForUpdate(List<Member> memberList, Logger LOGGER) {
		MemberOperationPojo passwordChecking = new MemberOperationPojo();
		try {
			for (Member checkingMember : memberList) {
				if (!isRequestContainsPassword(checkingMember)) {

					throw new CustomException(ErrorCodes.ERROR_12.getErrorCode(),
							resourceBundleMessageManager.getValueOfProperty(ErrorCodes.ERROR_12.getErrorCode(),
									checkingMember.getMemberLanguageCode()));
				}
			}
		} catch (CustomException customException) {
			passwordChecking.setErrorCode(customException.getErrorCode());
			passwordChecking.setResult(customException.getErrorMessage());
		}
		return passwordChecking;
	}

	public List<MemberIdWrapp> getMemberIdListFromMemerList(List<Member> members) {
		List<MemberIdWrapp> memberIdList = new ArrayList<>();
		for (Member member : members) {
			MemberIdWrapp memberIdWrapp = new MemberIdWrapp();
			memberIdWrapp.setId(member.getId());
			memberIdList.add(memberIdWrapp);
		}
		return memberIdList;
	}

	public List<Member> removeFieldsFromReturningMember(List<Member> memberList) {
		for (Member member : memberList) {
			member.setPassword(null);
		}
		return memberList;
	}
}
