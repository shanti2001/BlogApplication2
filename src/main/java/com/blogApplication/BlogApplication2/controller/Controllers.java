package com.blogApplication.BlogApplication2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.blogApplication.BlogApplication2.entity.*;
import com.blogApplication.BlogApplication2.repository.PostRepository;
import com.blogApplication.BlogApplication2.repository.TagRepository;
import com.blogApplication.BlogApplication2.repository.UserRepository;
import com.blogApplication.BlogApplication2.service.PostService;
import com.blogApplication.BlogApplication2.service.TagService;
import com.blogApplication.BlogApplication2.service.UserService;

import java.util.*;

@Controller
public class Controllers {
//
//	@Autowired
//	UserRepository userRepository;
//	@Autowired
//	PostRepository postsRepository;
//	@Autowired
//	TagRepository tagsRepository;
//	@Autowired
//	PostService postService;
//	@Autowired
//	UserService userService;
//	@Autowired
//	TagService tagService;
//
//	@RequestMapping(value = "/")
//	public String home(Model model) {
//		List<Post> list = postsRepository.findAll();
//		model.addAttribute("posts",list);
//		return "home";
//	}
//	@RequestMapping(value = "/login")
//	public String login() {
//		return "login";
//	}
//	@RequestMapping(value = "/register")
//	public String register() {
//		return "register";
//	}
//	@PostMapping("/register")
//	public String check(@ModelAttribute User user) {
//		userRepository.save(user);
//		return "redirect:/login";
//	}
//
//	//	@GetMapping("/userpage")
//	//	public String getUserPgae() {
//	//		return "userPage";
//	//	}
//
//	//	@RequestMapping("/userpage")
//	//	public String userPage(@RequestParam String email, String password,  Model model) {
//	////		User user = userService.findByEmail(email);
//	////		if(user!=null && user.getPassword().equals(password)) {
//	////			model.addAttribute("user",user);
//	////		}
//	//		
//	//		List<Posts> list = postsRepository.findAll();
//	//		model.addAttribute("posts",list);
//	//		return "userPage";
//	//	}
//	//	
//
//	@RequestMapping("/userpage")
//	public String userPage(Model model) {
//		List<Post> list = postsRepository.findAll();
//		//		System.out.println(list.get(0).getAuthor().getName());
//		model.addAttribute("posts",list);
//		return "userPage";
//	}
//	@RequestMapping("/userblog")
//	public String userBlog( Model model) {
//		List<Post> list = postsRepository.findAll();
//		model.addAttribute("posts",list);
//		return "userBlog";
//	}
//
//	@GetMapping("/update/{id}")
//	public String updatePostForm(@PathVariable int id, Model model) {
//		Post post = postsRepository.findById(id).orElse(null);
//		model.addAttribute("post",post);
//		return "updateBlog";
//	}
//	@PostMapping("/update")
//	public String updateForm(@ModelAttribute("post") Post post) {
//		int postId = post.getId();
//		Post exitPost = postsRepository.findById(postId).orElse(null);
//		if(exitPost != null) {
//			exitPost.setContent(post.getContent());
//			exitPost.setExcerpt(post.getExcerpt());
//			exitPost.setTitle(post.getTitle());
//			exitPost.setUpdated_at(new Date());
//		}
//		postsRepository.save(exitPost);
//		return "redirect:/userpage"; 
//	}
//
//	@GetMapping("/delete/{id}")
//	public String deletePost(@PathVariable int id) {
//		postsRepository.deleteById(id);
//		return "redirect:/userblog";
//	}
//
//	@RequestMapping("/post")
//	public String post() {
//		return "newPost";
//	}
//	@PostMapping("/post")
//	public String submit(@RequestParam("tagInput") String tagInput, @ModelAttribute Post post) {
//
//		if(post.getPublished_at()==null) {
//			post.setPublished_at(new Date());
//		}
//		User author = new User();
//		author.setEmail("shanti2001samanta@gmail.com");
//		author.setPassword("1234");
//		author.setName("shanti");
//
//		post.setAuthor(author);
//
//		post.setCreated_at(new Date());
//		post.setUpdated_at(new Date());
//		post.setIs_published(true);
//
//		List<Post> posts = author.getPosts();
//		if(posts==null) {
//			posts = new ArrayList<>();
//		}
//		posts.add(post);
//		author.setPosts(posts);
//
//		userRepository.save(author);
//		postsRepository.save(post);
//		
//		
//		System.out.println(tagInput);
//		String[] tagsName = tagInput.split(",");
//		List<Tag> tags = tagsRepository.findAll();
//		List<Tag> postTags = new ArrayList();
//		System.out.println(tags.size());
//		if(tags.size() == 0) {
//			for(String tagName:tagsName) {
//				
//				Tag newTag = new Tag();
//				newTag.setCreated_At(new Date());
//				newTag.setName(tagName);
//				newTag.setUpdated_at(new Date());
//				System.out.println(newTag.getId()+" "+newTag.getName());
//
//				tagsRepository.save(newTag);
//				Tag tag = tagService.findByName(tagName);
//				System.out.println(tag.getName());
//				List<Post> tagposts = tag.getPosts();
//				if(tagposts==null) {
//					tagposts = new ArrayList<>();
//				}
//				tagposts.add(post);
//				postTags.add(tag);
//				tagsRepository.save(tag);
//
//			}
//			System.out.println("null");
//		}
//		else {
//			
//			for(String tagName:tagsName) {
//				for(Tag tag:tags) {
//					if(tag.getName().equals(tagName)) {
//						Tag myTag = tagsRepository.findById(tag.getId()).get();
//						System.out.println(myTag.getId()+" "+myTag.getName()+"if");
//						myTag.setUpdated_at(new Date());
//						tagsRepository.save(myTag);
//						
//						
//						Tag tagn = tagsRepository.findById(tag.getId()).get();
//						System.out.println(tagn.getName());
//						List<Post> tagposts = tagn.getPosts();
//						if(tagposts==null) {
//							tagposts = new ArrayList<>();
//						}
//						tagposts.add(post);
//						postTags.add(tagn);
//						tagsRepository.save(tagn);
//					}
////					else {
////						Tag newTag = new Tag();
////						newTag.setCreated_At(new Date());
////						newTag.setName(tagName);
////						newTag.setUpdated_at(new Date());
////						tagsRepository.save(newTag);
////						System.out.println(newTag.getId()+" "+newTag.getName()+"else");
////
////						
////						Tag tagn = tagService.findByName(tagName);
////						System.out.println(tagn.getName());
////						List<Post> tagposts = tag.getPosts();
////						if(tagposts==null) {
////							tagposts = new ArrayList<>();
////						}
////						tagposts.add(post);
////						postTags.add(tagn);
////						tagsRepository.save(tagn);
////					}
//				}
//				if(tagService.findByName(tagName)==null) {
//					Tag newTag = new Tag();
//					newTag.setCreated_At(new Date());
//					newTag.setName(tagName);
//					newTag.setUpdated_at(new Date());
//					tagsRepository.save(newTag);
//					System.out.println(newTag.getId()+" "+newTag.getName()+"else");
//
//					
//					Tag tagn = tagService.findByName(tagName);
//					System.out.println(tagn.getName());
//					List<Post> tagposts = tagn.getPosts();
//					if(tagposts==null) {
//						tagposts = new ArrayList<>();
//					}
//					tagposts.add(post);
//					postTags.add(tagn);
//					tagsRepository.save(tagn);
//				}
//				
//			}
//			System.out.println("not empty");
//		}
////		tags = tagsRepository.findAll();
//		post.setTags(postTags);
//		
//		postsRepository.save(post);
//		
//
//
//		return "redirect:/userpage";
//	}
//
//	@GetMapping("/blogpost/{id}")
//	public String blogPost(@PathVariable int id, Model model) {
//		Post post = postsRepository.findById(id).orElse(null);
//		if(post==null) {
//			return "redirect:/";
//		}
//		model.addAttribute("post",post);
//		return "blogPost";
//	}
//	@GetMapping("/search")
//	public String searchBlogPosts(@RequestParam(name = "q", required = false) String query, Model model) {
//		Set<Post> searchResults =	postService.findPostsByTitleOrContentOrAuthorOrTag(query);
//		model.addAttribute("searchBlogs", searchResults);
//		return "searchBlog";
//	}
//
//
//	@RequestMapping("/sortbypublisheddate")
//	public String sortByPublishedDateTome(@RequestParam(name = "q", required = false) String sortBy, Model model) {
//		List<Post> list = new ArrayList();
//		if(sortBy.equals("newest")) {
//			list = postsRepository.findAll(Sort.by(Sort.Direction.DESC, "publishedAt"));
//		}
//		if(sortBy.equals("oldest")) {
//			list = postsRepository.findAll(Sort.by(Sort.Direction.ASC, "publishedAt"));
//		}
//
//		model.addAttribute("posts",list);
//		return "home";
//	}




}
