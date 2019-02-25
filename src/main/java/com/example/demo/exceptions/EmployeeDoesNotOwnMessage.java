package com.example.demo.exceptions;

public class EmployeeDoesNotOwnMessage extends RuntimeException{

	private static final long serialVersionUID = -8874695884749550144L;
	
	public EmployeeDoesNotOwnMessage()
	{
		super("Employee does not own the message");
	}
}
