package com.example.demo.config.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.demo.models.Employee;

public class EmployeeUserPrincipal implements UserDetails {

	private static final long serialVersionUID = 312412623180407930L;
	
	@Autowired
	private  Employee employee;
	
	public EmployeeUserPrincipal(Employee employee) {
		this.employee = employee;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		final List<GrantedAuthority> authorities = 
				Collections.singletonList(new SimpleGrantedAuthority("User"));
		return authorities;
	}

	@Override
	public String getPassword() {
		return employee.getPassword();
	}

	@Override
	public String getUsername() {
		return employee.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public Employee getEmployee()
	{
		return employee;
	}

}
