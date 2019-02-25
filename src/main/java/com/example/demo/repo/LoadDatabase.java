package com.example.demo.repo;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.models.Employee;
import com.example.demo.models.Message;

@Configuration
@Slf4j
public class LoadDatabase {
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LoadDatabase.class);
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	@Bean
	CommandLineRunner initDatabase(EmployeeRepository repository, MessageRepository mRepository) {
		return args -> 
		{
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			Employee user = new Employee("Bilbo Baggins", "bilbo");
			user.setPassword(passwordEncoder.encode("password"));
			log.info("Preloading " + repository.save(user));
			
			List<Employee> employees = new ArrayList<Employee>();
			Employee user2 = new Employee("Frodo Baggins", "frodo");
			user2.setPassword(passwordEncoder.encode("password"));
			employees.add(user);
			user2.setFollowing(employees);
			log.info("Preloading " + repository.save(user2));
			log.info("Preloading " + mRepository.save(new Message(user2, "this is a message")));
			log.info("Preloading " + mRepository.save(new Message(user2, "this is a message")));
			
		};
	}
	
	private List<Employee> createEmployee(int num)
	{
		List<Employee> employees = new ArrayList<Employee>();
		for (int i=0; i < num; i++)
		{
			Employee user = new Employee("user"+i, "username"+i);
			user.setPassword(passwordEncoder.encode("password"));
			employees.add(user);
		}
		return employees;
	}
	
}