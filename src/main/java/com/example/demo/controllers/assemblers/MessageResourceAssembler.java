package com.example.demo.controllers.assemblers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.example.demo.controllers.MessageController;
import com.example.demo.models.Message;

@Component
public class MessageResourceAssembler implements ResourceAssembler<Message, Resource<Message>> 
{

	@Override
	public Resource<Message> toResource(Message message) {
		return new Resource<>(message,
				linkTo(methodOn(MessageController.class).getMessage(message.getId())).withSelfRel());
	}

}
