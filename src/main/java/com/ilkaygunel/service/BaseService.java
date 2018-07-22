package com.ilkaygunel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.ilkaygunel.application.ResourceBundleMessageManager;
import com.ilkaygunel.facade.MemberFacade;
import com.ilkaygunel.facade.MemberRoleFacade;
import com.ilkaygunel.util.LoggingUtil;
import com.ilkaygunel.util.MailUtil;
import com.ilkaygunel.util.MemberUtil;

@Service
public class BaseService {

	@Autowired
	protected MemberFacade memberFacade;

	@Autowired
	protected MemberRoleFacade memberRoleFacade;

	@Autowired
	protected MemberUtil memberUtil;

	@Autowired
	protected MailUtil mailUtil;

	@Autowired
	protected Environment environment;

	@Autowired
	public LoggingUtil loggingUtil;

	@Autowired
	protected MemberSaveService memberSaveService;

	@Autowired
	protected MemberRoleSaveService memberRoleSaveService;

	@Autowired
	protected ResourceBundleMessageManager resourceBundleMessageManager;

}
