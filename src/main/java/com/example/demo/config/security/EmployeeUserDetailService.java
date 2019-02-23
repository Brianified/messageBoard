package com.example.demo.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.models.Employee;
import com.example.demo.repo.EmployeeRepository;

@Service
public class EmployeeUserDetailService implements UserDetailsService {

	@Autowired
    private EmployeeRepository employeeRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee = employeeRepository.findByUsername(username);
		if (employee == null) {
            throw new UsernameNotFoundException(username);
        }
        return new EmployeeUserPrincipal(employee);
	}

}
