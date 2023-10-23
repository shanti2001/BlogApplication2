package com.blogApplication.BlogApplication2.controller;


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
import com.blogApplication.BlogApplication2.entity.Tag;
import com.blogApplication.BlogApplication2.repository.CommentRepository;
import com.blogApplication.BlogApplication2.repository.PostRepository;
import com.blogApplication.BlogApplication2.repository.TagRepository;
import com.blogApplication.BlogApplication2.repository.UserRepository;
import com.blogApplication.BlogApplication2.service.PostService;
import com.blogApplication.BlogApplication2.service.TagService;
import com.blogApplication.BlogApplication2.service.UserService;

@Controller
public class PostController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	PostRepository postsRepository;
	@Autowired
	TagRepository tagsRepository;
	@Autowired
	PostService postService;
	@Autowired
	UserService userService;
	@Autowired
	TagService tagService;
	@Autowired
	CommentRepository commentRepository;

	@PostMapping("/publishpost")
	public String addPost(@RequestParam("tagInput") String allTag, @ModelAttribute Post post) {
		postService.addPost(allTag, post);
		return "redirect:/";
	}
	@GetMapping("/update/{id}")
	public String updatePostForm(@PathVariable int id, Model model) {
        
		Post post = postsRepository.findById(id).orElse(null);
		List<Tag> tags = post.getTags();
		String allTags = "";
		for(int index=0;index<tags.size();index++) {
			if(index==tags.size()-1) {
				allTags+=tags.get(index).getName();
			}
			else {
				allTags+=tags.get(index).getName()+",";
			}
		}

		model.addAttribute("post",post);
		model.addAttribute("alltags",allTags);
		return "updateBlog";
	}
	@PostMapping("/update")
	public String updateForm(@RequestParam("tagInput") String allTag, @ModelAttribute Post post) {
		
		postService.updatePost(allTag,post);
		return "redirect:/"; 
	}

	@GetMapping("/delete/{id}")
	public String deletePost(@PathVariable int id) {
		Post post = postsRepository.findById(id).get();
		List<Comment> comments = post.getComments();
		for(Comment comment:comments) {
			commentRepository.delete(comment);
		}
		postsRepository.deleteById(id);
		return "redirect:/user/userblog";
	}

}
