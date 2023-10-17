package com.blogApplication.BlogApplication2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogApplication.BlogApplication2.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

}
