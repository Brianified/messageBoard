package com.example.demo.exceptions;

public class MessageNotFoundException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8142798372714370974L;

	public MessageNotFoundException(Long id){
		super("Could not find message " + id);
	}
}
