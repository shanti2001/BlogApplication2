package com.blogApplication.BlogApplication2.controller;



import java.util.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import jakarta.servlet.http.HttpSession;

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
	
	public User getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User author = userRepository.getUserByUserName(username);
        return author;
	}

	@RequestMapping("/")
	public String home(@RequestParam(name = "start", required = false, defaultValue = "1") int start,
			@RequestParam(name = "limit", required = false, defaultValue = "8") int limit ,Model model) {
		
        User author = getUser();
		
		Pageable pageable = PageRequest.of(start-1,limit);
		Page<Post> posts = postsRepository.findAll(pageable);
		model.addAttribute("pageCount",postService.getPageCount(limit));
		model.addAttribute("start",start);
		model.addAttribute("limit",limit);
		model.addAttribute("posts",posts);
		model.addAttribute("user",author);
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
			@RequestParam(name = "confirmPassword") String confirmPassword,Model model) {
		
		if(password.equals(confirmPassword) && userRepository.getUserByUserName(email)==null) {
			password = bCryptPasswordEncoder.encode(password);
			userService.addUser(name, email, password, password);
			return "redirect:/login";
		}
		else {
			model.addAttribute("error","Sorry! Email already in use");
			return "register";
		}
		
	}
	
	@RequestMapping("/newpost")
	public String newPost() {
		return "newPost";
	}

	@GetMapping("/blogpost/{id}")
	public String showAllOfOnePost(@PathVariable int id, Model model) {
		
		User author = getUser();
		Post post = postsRepository.findById(id).orElse(null);
		if(post==null) {
			return "redirect:/";
		}
		model.addAttribute("post",post);
		model.addAttribute("user",author);
		return "blogPost";
	}

	@RequestMapping("/search")
	public String searchBlogPosts(@RequestParam(name = "q", required = false) String query,
			@RequestParam(name = "start", required = false, defaultValue = "1") int start,
			@RequestParam(name = "limit", required = false, defaultValue = "8") int limit , Model model) {
		
		User author = getUser();
		Set<Post> searchResults =	postService.findPostsByTitleOrContentOrAuthorOrTag(query);
		List<Post> postslist = new ArrayList<>(searchResults);
		int fromIndex = Math.min((start-1) * limit, searchResults.size());
		int toIndex = Math.min(fromIndex + limit, searchResults.size());
		List<Post> content = postslist.subList(fromIndex, toIndex);
		Page<Post> pageOfPosts = new PageImpl<>(content, PageRequest.of(start-1, limit), searchResults.size());
		
		model.addAttribute("pageCount",postService.getPageCount(limit));
		model.addAttribute("search",query);
		model.addAttribute("start",start);
		model.addAttribute("limit",limit);
		model.addAttribute("user",author);
		
		model.addAttribute("searchBlogs", pageOfPosts);
		return "searchBlog";
	}

	@RequestMapping("/sortby")
	public String getAllPostsSortedByPublishedDate(@RequestParam(name = "sort", required = false) String sortBy,
			@RequestParam(name = "start", required = false, defaultValue = "1") int start,
			@RequestParam(name = "limit", required = false, defaultValue = "8") int limit ,Model model) {
		
		List<Post> posts = postService.getAllPostsSortedByPublishDate(sortBy);
		User author = getUser();
		
		int fromIndex = Math.min((start-1) * limit, posts.size());
		int toIndex = Math.min(fromIndex + limit, posts.size());
		List<Post> content = posts.subList(fromIndex, toIndex);
		Page<Post> pageOfPosts = new PageImpl<>(content, PageRequest.of(start-1, limit), posts.size());
		
		model.addAttribute("pageCount",postService.getPageCount(limit));
		model.addAttribute("sort",sortBy);
		model.addAttribute("start",start);
		model.addAttribute("limit",limit);
		model.addAttribute("posts", pageOfPosts);
		model.addAttribute("user",author);
		return "home";
	}
	@RequestMapping("/filter")
	public String filter(Model model) {
		List<String> users = userService.uniqueAuthorName();
		User author = getUser();
		List<Date> publishDate = postsRepository.findPublishDate();
		List<Tag> tags = tagRepository.findAll();
		model.addAttribute("users",users);
		model.addAttribute("publishDate",publishDate);
		model.addAttribute("tags",tags);
		model.addAttribute("user",author);
		return "selectFilter";
	}
	@RequestMapping("/filterby")
	public String filterBy(@RequestParam(name = "authorName", required = false) String[] authorsName,
			@RequestParam(name = "publishDate", required = false) String[] publishDates ,
			@RequestParam(name = "tagId", required = false) String[] tagsId ,Model model) {
		User author = getUser();
		if(authorsName ==null && publishDates== null && tagsId==null) {
			List<Post> posts = postsRepository.findAll();
			model.addAttribute("posts",posts);
		}
		else {
			Set<Post> posts = postService.filterByAuthorAndTags(authorsName,publishDates,tagsId);
			model.addAttribute("posts",posts);
		}
		model.addAttribute("user",author);
		
		return "home";
	}
	
}
