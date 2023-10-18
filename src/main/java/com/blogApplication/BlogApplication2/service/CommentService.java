package com.blogApplication.BlogApplication2.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogApplication.BlogApplication2.entity.Comment;
import com.blogApplication.BlogApplication2.entity.Post;
import com.blogApplication.BlogApplication2.repository.CommentRepository;
import com.blogApplication.BlogApplication2.repository.PostRepository;

@Service
public class CommentService {
	@Autowired
	PostRepository postRepository;
	@Autowired
	CommentRepository commentRepository;
	public void addComment(int id,String name,String email,String comment) {
		Post post = postRepository.findById(id).get();
		Comment newComment = new Comment();
		newComment.setName(name);
		newComment.setComment(comment);
		newComment.setEmail(email);
		newComment.setCreated_at(new Date());
		newComment.setUpdated_at(new Date());
		newComment.setPost(post);
		
		commentRepository.save(newComment);
		
		List<Comment> postComments = post.getComments();
		if(postComments==null) {
			postComments = new ArrayList<>();
		}
		postComments.add(newComment);
		postRepository.save(post);
	}
	
	public void updateComment(Comment exitComment,String name,String email,String comment) {
		if(exitComment != null) {
			exitComment.setComment(comment);
			exitComment.setEmail(email);
			exitComment.setName(name);
			exitComment.setUpdated_at(new Date());
		}
		commentRepository.save(exitComment);
	}
}
