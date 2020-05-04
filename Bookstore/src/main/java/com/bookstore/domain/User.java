package com.bookstore.domain;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bookstore.domain.security.Authority;
import com.bookstore.domain.security.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User extends DomainEntity implements UserDetails{
	
	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String gender;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthDate;
	@Embedded
	private Telephone telephone;

	@Column(name="email", nullable = false, updatable = false)
	private String email;
	private String cpf;
	private boolean enabled=true;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
	private ShoppingCart shoppingCart;

	@OneToMany(mappedBy = "user")
	private List<ExchangeCoupon> exchangeCoupons;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<UserShipping> userShippingList;
	
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<UserPayment> userPaymentList;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<UserRole> userRoles = new HashSet<>();
	
	@OneToMany(mappedBy = "user")
	private List<Order> orderList;

	@OneToMany(mappedBy = "user")
	private List<Exchange> exchangeList;

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public List<ExchangeCoupon> getExchangeCoupons() {
		return exchangeCoupons;
	}

	public void setExchangeCoupons(List<ExchangeCoupon> exchangeCoupons) {
		this.exchangeCoupons = exchangeCoupons;
	}


	public List<UserShipping> getUserShippingList() {
		return userShippingList;
	}

	public void setUserShippingList(List<UserShipping> userShippingList) {
		this.userShippingList = userShippingList;
	}

	public List<UserPayment> getUserPaymentList() {
		return userPaymentList;
	}

	public void setUserPaymentList(List<UserPayment> userPaymentList) {
		this.userPaymentList = userPaymentList;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	public List<Exchange> getExchangeList() {
		return exchangeList;
	}

	public void setExchangeList(List<Exchange> exchangeList) {
		this.exchangeList = exchangeList;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public Telephone getTelephone() {
		return telephone;
	}

	public void setTelephone(Telephone telephone) {
		this.telephone = telephone;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorites = new HashSet<>();
		userRoles.forEach(ur -> authorites.add(new Authority(ur.getRole().getName())));
		
		return authorites;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	
}
