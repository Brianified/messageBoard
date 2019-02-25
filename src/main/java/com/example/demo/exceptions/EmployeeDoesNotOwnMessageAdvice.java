package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EmployeeDoesNotOwnMessageAdvice {
	@ResponseBody
	@ExceptionHandler(EmployeeDoesNotOwnMessage.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	String employeeDoesNotOwnMessageHandler(EmployeeDoesNotOwnMessage ex) {
		return ex.getMessage();
	}
}
