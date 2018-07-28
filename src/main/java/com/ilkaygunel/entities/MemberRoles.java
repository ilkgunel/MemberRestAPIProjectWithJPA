package com.ilkaygunel.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@NamedQueries({
		@NamedQuery(name = "MemberRoles.findByEmail", query = "select mr from MemberRoles mr where mr.email = :email") })
@Table(name = "MEMBERROLES")
public class MemberRoles {
	@Id
	@Column(name = "MEMBERROLES_ID")
	private long id;
	private String email;
	private String role;

	@MapsId
	@OneToOne // (mappedBy = "roleOfMember")
	@JoinColumn(name = "MEMBERROLES_ID")
	@JsonIgnore
	private Member member;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
}
