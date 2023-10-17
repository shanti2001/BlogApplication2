package com.blogApplication.BlogApplication2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogApplication.BlogApplication2.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer>{
	
	public Tag findByName(String name);
	

}
