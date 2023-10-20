package com.blogApplication.BlogApplication2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blogApplication.BlogApplication2.entity.Post;
import com.blogApplication.BlogApplication2.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer>{
	
	public Tag findByName(String name);
	
	@Query("select t.posts from Tag t where t.name = :name")
	public List<Post> findByTagName(String name);

}
