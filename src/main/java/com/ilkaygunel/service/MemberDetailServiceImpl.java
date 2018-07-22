package com.ilkaygunel.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ilkaygunel.entities.Member;

@Service
public class MemberDetailServiceImpl extends BaseService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
		Map<Object, Object> parameterMap = new HashMap<Object, Object>();
		parameterMap.put("email", emailAddress);
		Member member = memberFacade.findListByNamedQuery("Member.findByEmail", parameterMap).get(0);
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority(member.getRoleOfMember().getRole()));
		return new User(member.getEmail(), member.getPassword(), member.isEnabled(), true, true, true,
				grantedAuthorities);
	}
}
