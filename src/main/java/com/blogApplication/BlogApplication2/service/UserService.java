package com.blogApplication.BlogApplication2.service;

import java.util.*;

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
	
	public void addUser(String name,String email,String password,String confirmPassword) {
		User newUser = new User();
		newUser.setName(name);
		newUser.setEmail(email);
		newUser.setPassword(password);
		userRepository.save(newUser);
	}
	public List<String> uniqueAuthorName(){
		List<User> allUser = userRepository.findAll();
		List<String> uniqueUsers = new ArrayList<>();
		for(User user:allUser) {
			if(!uniqueUsers.contains(user.getName())) {
				uniqueUsers.add(user.getName());
			}
		}
		return uniqueUsers;
	}
}
