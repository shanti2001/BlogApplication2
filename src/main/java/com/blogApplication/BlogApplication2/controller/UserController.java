package com.blogApplication.BlogApplication2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blogApplication.BlogApplication2.entity.Post;
import com.blogApplication.BlogApplication2.entity.User;
import com.blogApplication.BlogApplication2.repository.PostRepository;
import com.blogApplication.BlogApplication2.repository.UserRepository;
import com.blogApplication.BlogApplication2.service.PostService;
import com.blogApplication.BlogApplication2.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	PostRepository postsRepository;
	@Autowired
	PostService postService;
	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;
	
	
	@RequestMapping("/userpage")
	public String userPage(@RequestParam(name = "start", required = false, defaultValue = "1") int start,
			@RequestParam(name = "limit", required = false, defaultValue = "8") int limit ,Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User author = userRepository.getUserByUserName(username);
		Pageable pageable = PageRequest.of(start-1,limit);
		Page<Post> posts = postsRepository.findAll(pageable);
		model.addAttribute("pageCount",postService.getPageCount(limit,postsRepository.findAll().size()));
		model.addAttribute("start",start);
		model.addAttribute("limit",limit);
		model.addAttribute("posts",posts);
		model.addAttribute("user",author);
		return "userPage";
	}
	@RequestMapping("/userblog")
	public String userBlog(@RequestParam(name = "start", required = false, defaultValue = "1") int start,
			@RequestParam(name = "limit", required = false, defaultValue = "8") int limit ,Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User author = userRepository.getUserByUserName(username);
        
        List<Post> userPosts = author.getPosts();
	if(author.getRoles().equals("ROLE_ADMIN")) {
        	userPosts = postsRepository.findAll(); 
        }

        int fromIndex = Math.min((start-1) * limit, userPosts.size());
		int toIndex = Math.min(fromIndex + limit, userPosts.size());
		List<Post> content = userPosts.subList(fromIndex, toIndex);
		Page<Post> pageOfPosts = new PageImpl<>(content, PageRequest.of(start-1, limit), userPosts.size());
		
		model.addAttribute("pageCount",postService.getPageCount(limit,userPosts.size()));
		model.addAttribute("start",start);
		model.addAttribute("limit",limit);
		model.addAttribute("posts",pageOfPosts);
		model.addAttribute("user",author);
		return "userBlog";
	}
}
