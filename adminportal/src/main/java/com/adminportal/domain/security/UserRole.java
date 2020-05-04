package com.adminportal.domain.security;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.adminportal.domain.DomainEntity;
import com.adminportal.domain.User;


@Entity
@Table(name="user_role")
public class UserRole extends DomainEntity {

	private static final long serialVersionUID = 1L;

	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="role_id")
	private Role role;
	
	public UserRole(){}
	
	public UserRole(User user, Role role) {
		this.user = user;
		this.role = role;
	}



	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}
	
	
}
