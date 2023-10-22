package com.blogApplication.BlogApplication2.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blogApplication.BlogApplication2.entity.Comment;
import com.blogApplication.BlogApplication2.entity.Post;
import com.blogApplication.BlogApplication2.entity.User;
import com.blogApplication.BlogApplication2.repository.CommentRepository;
import com.blogApplication.BlogApplication2.repository.PostRepository;
import com.blogApplication.BlogApplication2.repository.UserRepository;
import com.blogApplication.BlogApplication2.service.CommentService;


@Controller
public class CommentController {

	@Autowired
	CommentRepository commentRepository;
	@Autowired
	PostRepository postRepository;
	@Autowired
	CommentService commentService;
	@Autowired
	UserRepository userRepository;

	@PostMapping("/comment")
	public String addComment(@RequestParam(name = "id") int id,
			@RequestParam(name = "name") String name,
			@RequestParam(name = "email") String email,
			@RequestParam(name = "comment") String comment) {
		
		commentService.addComment(id, name, email, comment);
		return "redirect:/blogpost/"+id;
	}
	
	@GetMapping("/showcomment/{id}")
	public String readComment(@PathVariable int id, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User author = userRepository.getUserByUserName(username);
        
		Post post = postRepository.findById(id).orElse(null);
		if(post==null) {
			return "redirect:/";
		}
		
		model.addAttribute("comments",post.getComments());
		model.addAttribute("title",post.getTitle());
		model.addAttribute("author",author);
		return "Comment";
	}
	
	@GetMapping("/updatecomment/{id}")
	public String updateCommentForm(@PathVariable int id, Model model) {
		Comment comment = commentRepository.findById(id).get();
		model.addAttribute(comment);
		return "updateComment";
		
	}
	@PostMapping("/updatecomment")
	public String updateComment(@RequestParam(name = "id") int id,
			@RequestParam(name = "name") String name,
			@RequestParam(name = "email") String email,
			@RequestParam(name = "comment") String comment ) {
		
		Comment exitComment = commentRepository.findById(id).get();
		commentService.updateComment(exitComment, name, email, comment);
		return "redirect:/showcomment/"+exitComment.getPost().getId(); 
	}

	@GetMapping("/deletecomment/{id}")
	public String deleteComment(@PathVariable int id) {
		Comment deleteComment = commentRepository.findById(id).get();
		commentRepository.deleteById(id);
		return "redirect:/showcomment/"+deleteComment.getPost().getId();
	}
}
