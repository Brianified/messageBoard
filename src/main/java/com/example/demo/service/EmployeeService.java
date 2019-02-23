package com.example.demo.service;

import java.util.List;

import com.example.demo.models.Employee;

public interface EmployeeService 
{
	public List<Employee> getAllEmployees();
	public Employee newEmployee(Employee employee);
	public Employee getEmployee(String username);
	public Employee updateEmployee(Employee employee, String currentUser);
	public void deleteEmployee(Long id);
	public void followEmployee(String following, String currentUser);
}
