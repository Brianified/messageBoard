package com.example.demo.exceptions;

public class EmployeeNotFoundException extends RuntimeException
{
	
	private static final long serialVersionUID = -5172959998660914288L;

	public EmployeeNotFoundException(Long id)
	{
		super("Could not find employee " + id);
	}
	
	public EmployeeNotFoundException(String username)
	{
		super("Could not find username " + username);
	}
}
