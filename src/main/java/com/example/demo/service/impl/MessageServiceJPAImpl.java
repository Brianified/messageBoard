package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.exceptions.MessageNotFoundException;
import com.example.demo.models.Employee;
import com.example.demo.models.Message;
import com.example.demo.repo.MessageRepository;
import com.example.demo.service.MessageService;

public class MessageServiceJPAImpl implements MessageService {

	private final MessageRepository repository;
	
	public MessageServiceJPAImpl(MessageRepository repository) 
	{
		this.repository = repository;
	}
	
	@Override
	public List<Message> getFeed(Employee employee) {
		
		List<Employee> following = employee.getFollowing();
		following.add(employee);
		List<Long> ids=following.stream().map(Employee::getId).collect(Collectors.toList());
		return repository.findTop100ByUser_IdInOrderByPostTimeDesc(ids);
	}
	
	@Override
	public Message newMessage(Message message){
		return repository.save(message);
	}

	@Override
	public Message getMessage(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new MessageNotFoundException(id));
	}

	@Override
	public Message replaceMessage(Long id, Message newMessage) {
		return repository.findById(id)
				.map(message -> {
					message.setUser(newMessage.getUser());
					message.setMessage(newMessage.getMessage());
					return repository.save(message);
				})
				.orElseGet(() -> {
					newMessage.setId(id);
					return repository.save(newMessage);
				});
	}

	@Override
	public void deleteMessage(Long id) {
		repository.deleteById(id);
	}

}
