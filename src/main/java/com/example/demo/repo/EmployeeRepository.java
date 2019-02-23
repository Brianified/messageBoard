package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Employee;
public interface EmployeeRepository extends JpaRepository<Employee, Long> 
{
	Employee findByUsername(String username);
}
