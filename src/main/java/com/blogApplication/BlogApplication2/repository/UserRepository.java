package com.blogApplication.BlogApplication2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blogApplication.BlogApplication2.entity.Post;
import com.blogApplication.BlogApplication2.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	public User findByEmailContaining(String email);
	
	@Query("select u from User u where u.email = :email")
	public User getUserByUserName(String email);
	
	@Query("select u.posts from User u where u.name = :name")
	public List<Post> findByAuthorName(String name);
}
