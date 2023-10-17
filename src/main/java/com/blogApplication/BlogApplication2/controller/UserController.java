package com.blogApplication.BlogApplication2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blogApplication.BlogApplication2.entity.Post;
import com.blogApplication.BlogApplication2.repository.PostRepository;

@Controller
public class UserController {

	@Autowired
	PostRepository postsRepository;

	@RequestMapping("/userpage")
	public String userPage(Model model) {
		List<Post> list = postsRepository.findAll();
		model.addAttribute("posts",list);
		return "userPage";
	}
	@RequestMapping("/userblog")
	public String userBlog( Model model) {
		List<Post> list = postsRepository.findAll();
		model.addAttribute("posts",list);
		return "userBlog";
	}
}
