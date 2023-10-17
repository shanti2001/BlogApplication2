package com.blogApplication.BlogApplication2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blogApplication.BlogApplication2.entity.Post;
import com.blogApplication.BlogApplication2.entity.User;
import com.blogApplication.BlogApplication2.repository.PostRepository;
import com.blogApplication.BlogApplication2.repository.UserRepository;
import com.blogApplication.BlogApplication2.service.PostService;

@Controller
public class HomeContrller {
	@Autowired
	UserRepository userRepository;
	@Autowired
	PostRepository postsRepository;
	@Autowired
	PostService postService;

	@RequestMapping(value = "/")
	public String home(Model model) {
		List<Post> list = postsRepository.findAll();
		model.addAttribute("posts",list);
		return "home";
	}
	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}
	@RequestMapping(value = "/register")
	public String register() {
		return "register";
	}
	@PostMapping("/register")
	public String check(@ModelAttribute User user) {
		userRepository.save(user);
		return "redirect:/login";
	}
	
	@RequestMapping("/post")
	public String post() {
		return "newPost";
	}

	@GetMapping("/blogpost/{id}")
	public String blogPost(@PathVariable int id, Model model) {
		Post post = postsRepository.findById(id).orElse(null);
		if(post==null) {
			return "redirect:/";
		}
		model.addAttribute("post",post);
		return "blogPost";
	}

	@GetMapping("/search")
	public String searchBlogPosts(@RequestParam(name = "q", required = false) String query, Model model) {
		Set<Post> searchResults =	postService.findPostsByTitleOrContentOrAuthorOrTag(query);
		model.addAttribute("searchBlogs", searchResults);
		return "searchBlog";
	}


	@RequestMapping("/sortbypublisheddate")
	public String sortByPublishedDateTome(@RequestParam(name = "q", required = false) String sortBy, Model model) {
		List<Post> list = new ArrayList();
		if(sortBy.equals("newest")) {
			list = postsRepository.findAll(Sort.by(Sort.Direction.DESC, "publishedAt"));
		}
		if(sortBy.equals("oldest")) {
			list = postsRepository.findAll(Sort.by(Sort.Direction.ASC, "publishedAt"));
		}

		model.addAttribute("posts",list);
		return "home";
	}
}
