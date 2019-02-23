package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.exceptions.EmployeeAlreadyFollowedException;
import com.example.demo.exceptions.EmployeeNotFoundException;
import com.example.demo.models.Employee;
import com.example.demo.repo.EmployeeRepository;
import com.example.demo.service.EmployeeService;

public class EmployeeServiceJPAImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository repository; 
	
	@Override
	public List<Employee> getAllEmployees() {
		
		return repository.findAll();
	}

	@Override
	public Employee newEmployee(Employee employee) {
		
		return repository.save(employee);
	}

	@Override
	public Employee getEmployee(String username) {
		
		Employee employee = findEmployeeByName(username);
		return employee;
	}

	@Override
	public Employee updateEmployee(Employee employee, String currentUser) {
		Employee updatedEmployee = findEmployeeByName(currentUser);
		updatedEmployee.setName(employee.getName());
		updatedEmployee.setPassword(employee.getPassword());
		repository.save(updatedEmployee);
		return updatedEmployee;
	}

	@Override
	public void deleteEmployee(Long id) {
		repository.deleteById(id);
	}

	@Override
	public void followEmployee(String followingUser, String currentUser) {
		Employee user = repository.findByUsername(currentUser);
		Employee follower = findEmployeeByName(followingUser);
		List<Employee> following = user.getFollowing();
		if(following.contains(follower))
		{
			throw new EmployeeAlreadyFollowedException(currentUser, followingUser);
		}
		following.add(follower);
		repository.save(user);
	}
	
	private Employee findEmployeeByName(String name) 
	{
		Employee employee = repository.findByUsername(name);
		if (employee == null)
		{
			throw new EmployeeNotFoundException(name);
		}
		return employee;
	}

}
