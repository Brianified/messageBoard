package com.example.demo.exceptions;

public class EmployeeAlreadyFollowedException extends RuntimeException {

	private static final long serialVersionUID = 1503642539261730611L;
	
	public EmployeeAlreadyFollowedException(String employee, String follower)
	{
		super("User " + employee + "is already following "+ follower);
	}
}
