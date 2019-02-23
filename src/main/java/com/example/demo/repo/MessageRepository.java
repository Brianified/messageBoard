package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Message;

public interface MessageRepository extends JpaRepository<Message, Long>
{
	List<Message> findTop100ByUser_IdInOrderByPostTimeDesc(List<Long> userId);
}
