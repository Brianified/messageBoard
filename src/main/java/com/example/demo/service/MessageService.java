package com.example.demo.service;

import java.util.List;

import com.example.demo.exceptions.MessageNotFoundException;
import com.example.demo.models.Employee;
import com.example.demo.models.Message;

public interface MessageService 
{
	public List<Message> getFeed(Employee user) throws MessageNotFoundException;
	
	public Message newMessage(Message message);
	
	public Message getMessage(Long id) throws MessageNotFoundException;
	
	public Message replaceMessage(Long id, Message newMessage) throws MessageNotFoundException;
	
	public void deleteMessage(Long id) throws MessageNotFoundException;
}
