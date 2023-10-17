package com.blogApplication.BlogApplication2.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blogApplication.BlogApplication2.entity.Comment;
import com.blogApplication.BlogApplication2.entity.Post;
import com.blogApplication.BlogApplication2.repository.CommentRepository;
import com.blogApplication.BlogApplication2.repository.PostRepository;


@Controller
public class CommentController {

	@Autowired
	CommentRepository commentRepository;
	@Autowired
	PostRepository postRepository;

	@PostMapping("/comment")
	public String addComment(@RequestParam(name = "id") int id,
			@RequestParam(name = "name") String name,
			@RequestParam(name = "email") String email,
			@RequestParam(name = "comment") String comment) {
		
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
	
		return "redirect:/blogpost/"+id;
	}
	
	@GetMapping("/showcomment/{id}")
	public String readComment(@PathVariable int id, Model model) {
		Post post = postRepository.findById(id).orElse(null);
		if(post==null) {
			return "redirect:/";
		}
		
		model.addAttribute("comments",post.getComments());
		model.addAttribute("title",post.getTitle());
		return "Comment";
	}
	
	@GetMapping("/updatecomment/{id}")
	public String updatePostForm(@PathVariable int id, Model model) {
		Comment comment = commentRepository.findById(id).get();
		model.addAttribute(comment);
		return "updateComment";
		
	}
	@PostMapping("/updatecomment")
	public String updateForm(@RequestParam(name = "id") int id,
			@RequestParam(name = "name") String name,
			@RequestParam(name = "email") String email,
			@RequestParam(name = "comment") String comment ) {
		Comment exitComment = commentRepository.findById(id).get();
		if(exitComment != null) {
			exitComment.setComment(comment);
			exitComment.setEmail(email);
			exitComment.setName(name);
			exitComment.setUpdated_at(new Date());
		}
		commentRepository.save(exitComment);
		return "redirect:/showcomment/"+exitComment.getPost().getId(); 
	}

	@GetMapping("/deletecomment/{id}")
	public String deletePost(@PathVariable int id) {
		Comment deleteComment = commentRepository.findById(id).get();
		commentRepository.deleteById(id);
		return "redirect:/showcomment/"+deleteComment.getPost().getId();
	}
}
