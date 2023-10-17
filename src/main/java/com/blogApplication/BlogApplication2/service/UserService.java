package com.blogApplication.BlogApplication2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogApplication.BlogApplication2.entity.User;
import com.blogApplication.BlogApplication2.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	
	public User findByEmail(String email) {
		return userRepository.findByEmailContaining(email);
	}
}
