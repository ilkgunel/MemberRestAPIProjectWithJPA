package com.ilkaygunel.entities;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@JsonInclude(Include.NON_NULL)
@NamedQueries({ @NamedQuery(name = "Member.findAll", query = "select m from Member m"),
		@NamedQuery(name = "Member.findByFirstName", query = "select m from Member m where m.firstName =:firstName"),
		@NamedQuery(name = "Member.findPasswordOfMember", query = "select m.password from Member m where m.id =:id"),
		@NamedQuery(name = "Member.findByActivationToken", query = "select m from Member m where m.activationToken =:activationToken"),
		@NamedQuery(name = "Member.findByEmail", query = "select m from Member m where m.email =:email") })
@Table(name = "MEMBER")
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MEMBER_ID")
	private long id;
	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;
	@Column(nullable = false, updatable = false)
	private String email;
	@Column(nullable = false, updatable = false)
	private boolean enabled;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String memberLanguageCode;
	@Column(updatable = false)
	@JsonIgnore
	private String activationToken;
	@Column(updatable = false)
	@JsonIgnore
	private LocalDateTime activationTokenExpDate;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "member")
	@PrimaryKeyJoinColumn
	private MemberRoles roleOfMember;

	@Override
	public String toString() {
		return String.format("Member [id=%d, firstName='%s', lastName='%s', email='%s']", id, firstName, lastName,
				email);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
		if (null != roleOfMember) {
			roleOfMember.setId(id);
		}
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public MemberRoles getRoleOfMember() {
		return roleOfMember;
	}

	public void setRoleOfMember(MemberRoles roleOfMember) {
		this.roleOfMember = roleOfMember;
	}

	public String getActivationToken() {
		return activationToken;
	}

	public void setActivationToken(String activationToken) {
		this.activationToken = activationToken;
	}

	public LocalDateTime getActivationTokenExpDate() {
		return activationTokenExpDate;
	}

	public void setActivationTokenExpDate(LocalDateTime activationTokenExpDate) {
		this.activationTokenExpDate = activationTokenExpDate;
	}

	public String getMemberLanguageCode() {
		return memberLanguageCode;
	}

	public void setMemberLanguageCode(String memberLanguageCode) {
		this.memberLanguageCode = memberLanguageCode;
	}
}
