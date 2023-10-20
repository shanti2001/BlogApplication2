package com.blogApplication.BlogApplication2.controller;



import java.util.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blogApplication.BlogApplication2.entity.Post;
import com.blogApplication.BlogApplication2.entity.Tag;
import com.blogApplication.BlogApplication2.entity.User;
import com.blogApplication.BlogApplication2.repository.PostRepository;
import com.blogApplication.BlogApplication2.repository.TagRepository;
import com.blogApplication.BlogApplication2.repository.UserRepository;
import com.blogApplication.BlogApplication2.service.PostService;
import com.blogApplication.BlogApplication2.service.UserService;

@Controller
public class HomeContrller {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PostRepository postsRepository;
	@Autowired
	PostService postService;
	@Autowired
	UserService userService;
	@Autowired
	TagRepository tagRepository;

	@RequestMapping("/")
	public String home(@RequestParam(name = "start", required = false, defaultValue = "1") int start,
			@RequestParam(name = "limit", required = false, defaultValue = "4") int limit ,Model model) {

		Pageable pageable = PageRequest.of(start-1,limit);
		Page<Post> posts = postsRepository.findAll(pageable);
		model.addAttribute("pageCount",postService.getPageCount(limit));
		model.addAttribute("start",start);
		model.addAttribute("limit",limit);
		model.addAttribute("posts",posts);
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
	public String addUser(@RequestParam(name = "name") String name,
			@RequestParam(name = "email") String email,
			@RequestParam(name = "password") String password,
			@RequestParam(name = "confirmPassword") String confirmPassword) {
		
		if(password.equals(confirmPassword)) {
			password = bCryptPasswordEncoder.encode(password);
			userService.addUser(name, email, password, password);
			return "redirect:/login";
		}
		else {
			return "register";
		}
		
	}
	
	@RequestMapping("/newpost")
	public String newPost() {
		return "newPost";
	}

	@GetMapping("/blogpost/{id}")
	public String showAllOfOnePost(@PathVariable int id, Model model) {
		Post post = postsRepository.findById(id).orElse(null);
		if(post==null) {
			return "redirect:/";
		}
		model.addAttribute("post",post);
		return "blogPost";
	}

	@GetMapping("/search")
	public String searchBlogPosts(@RequestParam(name = "q", required = false) String query,
			@RequestParam(name = "start", required = false, defaultValue = "1") int start,
			@RequestParam(name = "limit", required = false, defaultValue = "4") int limit , Model model) {
		
		
		Set<Post> searchResults =	postService.findPostsByTitleOrContentOrAuthorOrTag(query);
//		List<Post> pageResults = new ArrayList<>(searchResults).subList((start-1)*limit, Math.min((start-1)*limit+limit, searchResults.size()));
		
		model.addAttribute("pageCount",postService.getPageCount(limit));
		model.addAttribute("start",start);
		model.addAttribute("limit",limit);
		model.addAttribute("posts",searchResults);
		
		model.addAttribute("searchBlogs", searchResults);
		return "searchBlog";
	}

	@RequestMapping("/sortby")
	public String getAllPostsSortedByPublishedDate(@RequestParam(name = "sort", required = false) String sortBy, Model model) {
		List<Post> posts = postService.getAllPostsSortedByPublishDate(sortBy);
		model.addAttribute("posts", posts);
		return "home";
	}
	@RequestMapping("/filter")
	public String filter(Model model) {
		List<User> users = userRepository.findAll();
		List<Date> publishDate = postsRepository.findPublishDate();
		List<Tag> tags = tagRepository.findAll();
		model.addAttribute("users",users);
		model.addAttribute("publishDate",publishDate);
		model.addAttribute("tags",tags);
		return "selectFilter";
	}
	@RequestMapping("/filterby")
	public String filterBy(@RequestParam(name = "authorName", required = false) String[] authorsName,
			@RequestParam(name = "publishDate", required = false) String[] publishDates ,
			@RequestParam(name = "tagId", required = false) String[] tagsId ,Model model) {
		if(authorsName ==null && publishDates== null && tagsId==null) {
			List<Post> posts = postsRepository.findAll();
			model.addAttribute("posts",posts);
		}
		else {
			Set<Post> posts = postService.filterByAuthorAndTags(authorsName,publishDates,tagsId);
			model.addAttribute("posts",posts);
		}
		
		return "home";
	}
}
