package com.example.demo.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.JoinColumn;

@Data
@Entity
public class Employee {
	@ApiModelProperty(required = false, hidden = true)
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@JsonIgnore
	private String password;
	
	@JsonIgnore
	@OneToMany(mappedBy="user")
	private List<Message> messages;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "relation",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "following_id"))
    private List<Employee> following;
	
	public Employee() {}
	
	public Employee(String name, String username) {
		this.name = name;
		this.username = username;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public List<Employee> getFollowing() {
		if(following == null)
			following = new ArrayList<Employee>();
		return following;
	}

	public void setFollowing(List<Employee> following) {
		this.following = following;
	}

	@Override 
	public String toString() 
	{
	    return "Employee(id="+this.id+" name="+this.name+" username="+this.username+")";
	}
}
