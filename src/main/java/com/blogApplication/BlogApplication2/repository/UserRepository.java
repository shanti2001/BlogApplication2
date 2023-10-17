package com.blogApplication.BlogApplication2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogApplication.BlogApplication2.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	public User findByEmailContaining(String email);
}
