package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.repo.EmployeeRepository;
import com.example.demo.repo.MessageRepository;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.MessageService;
import com.example.demo.service.impl.EmployeeServiceJPAImpl;
import com.example.demo.service.impl.MessageServiceJPAImpl;

@Configuration
public class AppConfig {
	@Bean
	public MessageService messageService(MessageRepository repository)
	{
		return new MessageServiceJPAImpl(repository);
	}
	
	@Bean
	public EmployeeService employeeService(EmployeeRepository repository)
	{
		return new EmployeeServiceJPAImpl();
	}
}
