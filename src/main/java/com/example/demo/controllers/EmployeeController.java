package com.example.demo.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exceptions.EmployeeNotFoundException;
import com.example.demo.models.Employee;
import com.example.demo.models.NewUser;
import com.example.demo.service.EmployeeService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
public class EmployeeController {
	
	@Autowired
	private EmployeeService service;
	
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(11);

	@GetMapping("/employees")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Authorization bearer token", required = true, dataType = "string", paramType = "header")
      })
	List<Employee> all() {
		return service.getAllEmployees();
	}

	@PostMapping("/employees")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Authorization bearer token", required = true, dataType = "string", paramType = "header")
      })
	Employee newEmployee(@RequestBody Employee newEmployee) {
		return service.newEmployee(newEmployee);
	}

	@GetMapping("/employees/{username}")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Authorization bearer token", required = true, dataType = "string", paramType = "header")
      })
	Employee getEmployee(@PathVariable String username) {

		return service.getEmployee(username);
	}

	@PutMapping("/employees/{id}")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Authorization bearer token", required = true, dataType = "string", paramType = "header")
      })
	Employee replaceEmployee(Authentication authentication, @RequestBody Employee newEmployee, @PathVariable Long id) {
		String currentUser = authentication.getName();
		return service.updateEmployee(newEmployee, currentUser);
	}

	@DeleteMapping("/employees/{id}")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Authorization bearer token", required = true, dataType = "string", paramType = "header")
      })
	void deleteEmployee(@PathVariable Long id) {
		service.deleteEmployee(id);
	}
	
	@PutMapping("/employees/follow/{username}")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Authorization bearer token", required = true, dataType = "string", paramType = "header")
      })
	String followEmployee(Authentication authentication, @PathVariable String username) {
		String currentUser = authentication.getName();
		service.followEmployee(username, currentUser);
		return "you are now following " + username;
	}
	
	@PostMapping("/sign-up")
	Employee signUp(@RequestBody NewUser newEmployee) {
		Employee employee = new Employee();
		employee.setName(newEmployee.getName());
		employee.setUsername(newEmployee.getUsername());
		employee.setPassword(passwordEncoder.encode(newEmployee.getPassword()));
		return service.newEmployee(employee);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestBody Login login) throws RuntimeException {

		String jwtToken = "";

		if (login.getUsername() == null || login.getPassword() == null) {
			throw new RuntimeException("Please fill in username and password");
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String username = login.getUsername();
		String password = passwordEncoder.encode(login.getPassword());

		Employee user = service.getEmployee(username);

		if (user == null) {
			//throw new ServletException("User email not found.");
			throw new EmployeeNotFoundException(username);
		}

		String pwd = user.getPassword();

		if (!password.equals(pwd)) {
			throw new RuntimeException("Invalid login. Please check your name and password.");
		}

		jwtToken = Jwts.builder().setSubject(username).claim("roles", "user").setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, "secretkey").compact();

		return jwtToken;
	}
	
	private class Login
	{
		private String username;
		private String password;
		
		public String getUsername() {
			return username;
		}
		@SuppressWarnings("unused")
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		@SuppressWarnings("unused")
		public void setPassword(String password) {
			this.password = password;
		}
	}
	
}
