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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blogApplication.BlogApplication2.entity.Post;
import com.blogApplication.BlogApplication2.entity.Tag;
import com.blogApplication.BlogApplication2.entity.User;
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

	@GetMapping("/update/{id}")
	public String updatePostForm(@PathVariable int id, Model model) {
		Post post = postsRepository.findById(id).orElse(null);
		model.addAttribute("post",post);
		return "updateBlog";
	}
	@PostMapping("/update")
	public String updateForm(@ModelAttribute("post") Post post) {
		int postId = post.getId();
		Post exitPost = postsRepository.findById(postId).orElse(null);
		if(exitPost != null) {
			exitPost.setContent(post.getContent());
			exitPost.setExcerpt(post.getExcerpt());
			exitPost.setTitle(post.getTitle());
			exitPost.setUpdated_at(new Date());
		}
		postsRepository.save(exitPost);
		return "redirect:/userpage"; 
	}

	@GetMapping("/delete/{id}")
	public String deletePost(@PathVariable int id) {
		postsRepository.deleteById(id);
		return "redirect:/userblog";
	}

	@PostMapping("/post")
	public String submit(@RequestParam("tagInput") String tagInput, @ModelAttribute Post post) {

		if(post.getPublished_at()==null) {
			post.setPublished_at(new Date());
		}
		User author = new User();
		author.setEmail("shanti2001samanta@gmail.com");
		author.setPassword("1234");
		author.setName("shanti");

		post.setAuthor(author);

		post.setCreated_at(new Date());
		post.setUpdated_at(new Date());
		post.setIs_published(true);

		List<Post> posts = author.getPosts();
		if(posts==null) {
			posts = new ArrayList<>();
		}
		posts.add(post);
		author.setPosts(posts);

		userRepository.save(author);
		postsRepository.save(post);

		String[] tagsName = tagInput.split(",");
		List<Tag> tags = tagsRepository.findAll();
		List<Tag> postTags = new ArrayList();
		if(tags.size() == 0) {
			for(String tagName:tagsName) {
				Tag newTag = new Tag();
				newTag.setCreated_At(new Date());
				newTag.setName(tagName);
				newTag.setUpdated_at(new Date());
				tagsRepository.save(newTag);
				Tag tag = tagService.findByName(tagName);
				List<Post> tagposts = tag.getPosts();
				if(tagposts==null) {
					tagposts = new ArrayList<>();
				}
				tagposts.add(post);
				postTags.add(tag);
				tagsRepository.save(tag);
			}
		}
		else {

			for(String tagName:tagsName) {
				for(Tag tag:tags) {
					if(tag.getName().equals(tagName)) {
						Tag myTag = tagsRepository.findById(tag.getId()).get();
						myTag.setUpdated_at(new Date());
						tagsRepository.save(myTag);
						Tag tagn = tagsRepository.findById(tag.getId()).get();
						List<Post> tagposts = tagn.getPosts();
						if(tagposts==null) {
							tagposts = new ArrayList<>();
						}
						tagposts.add(post);
						postTags.add(tagn);
						tagsRepository.save(tagn);
					}

				}
				if(tagService.findByName(tagName)==null) {
					Tag newTag = new Tag();
					newTag.setCreated_At(new Date());
					newTag.setName(tagName);
					newTag.setUpdated_at(new Date());
					tagsRepository.save(newTag);
					Tag tagn = tagService.findByName(tagName);
					List<Post> tagposts = tagn.getPosts();
					if(tagposts==null) {
						tagposts = new ArrayList<>();
					}
					tagposts.add(post);
					postTags.add(tagn);
					tagsRepository.save(tagn);
				}

			}

		}
		post.setTags(postTags);
		postsRepository.save(post);
		return "redirect:/userpage";
	}
}
