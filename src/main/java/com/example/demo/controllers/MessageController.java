package com.example.demo.controllers;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controllers.assemblers.MessageResourceAssembler;
import com.example.demo.models.Employee;
import com.example.demo.models.Message;
import com.example.demo.service.EmployeeService;
import com.example.demo.service.MessageService;
import com.google.common.base.Preconditions;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
public class MessageController {
	
	private final MessageService service;
	
	private final MessageResourceAssembler assembler;
	
	@Autowired
	private EmployeeService employeeService;
	
	public MessageController(MessageService service, MessageResourceAssembler assembler)
	{
		this.service = service;
		this.assembler = assembler;
	}
	
	@GetMapping("/messages/feed/")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Authorization bearer token", required = true, dataType = "string", paramType = "header")
      })
	public Resources<Resource<Message>> feed(Authentication authentication) 
	{
		String username = authentication.getName();
		Employee user = employeeService.getEmployee(username);
		List<Resource<Message>> messages = service.getFeed(user).stream()
				.map(assembler::toResource)
				.collect(Collectors.toList());
		return new Resources<>(messages,
				linkTo(methodOn(MessageController.class).feed(authentication)).withSelfRel());
	}

	@PostMapping("/messages")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Authorization bearer token", required = true, dataType = "string", paramType = "header")
      })
	public Message newEmployee(Authentication authentication, @RequestBody NewMessage newMessage) {
		Preconditions.checkNotNull(newMessage);
		Message message = new Message();
		message.setMessage(newMessage.getMessage());
		Employee user = employeeService.getEmployee(authentication.getName());
		message.setUser(user);
		return service.newMessage(message);
	}

	@GetMapping("/messages/{id}")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Authorization bearer token", required = true, dataType = "string", paramType = "header")
      })
	public Resource<Message> getMessage(@PathVariable Long id) {

		Message message = service.getMessage(id);
		return assembler.toResource(message);
	}

	@PutMapping("/messages/{id}")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Authorization bearer token", required = true, dataType = "string", paramType = "header")
      })
	public Message replaceMessage(Authentication authentication, @RequestBody NewMessage newMessage, @PathVariable Long id) {
		Preconditions.checkNotNull(newMessage);
		String username = authentication.getName();
		Employee user = employeeService.getEmployee(username);
		Message message = new Message();
		message.setId(id);
		message.setUser(user);
		message.setMessage(newMessage.getMessage());
		return service.replaceMessage(id, message);
	}

	@DeleteMapping("/messages/{id}")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "Authorization", value = "Authorization bearer token", required = true, dataType = "string", paramType = "header")
      })
	public void deleteMessage(Authentication authentication, @PathVariable Long id) {
		String username = authentication.getName();
		Employee user = employeeService.getEmployee(username);
		service.deleteMessage(id, user);
	}
	
	private class NewMessage
	{
		private String message;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
}
