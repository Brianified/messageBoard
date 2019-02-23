package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EmployeeAlreadyFollowedAdvice {
	@ResponseBody
	@ExceptionHandler(EmployeeAlreadyFollowedException.class)
	@ResponseStatus(HttpStatus.NOT_MODIFIED)
	String employeeAlreadyFollowedExceptionHandler(EmployeeAlreadyFollowedException ex) {
		return ex.getMessage();
	}
}
