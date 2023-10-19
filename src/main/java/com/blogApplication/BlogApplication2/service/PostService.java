package com.blogApplication.BlogApplication2.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blogApplication.BlogApplication2.entity.Post;
import com.blogApplication.BlogApplication2.entity.Tag;
import com.blogApplication.BlogApplication2.entity.User;
import com.blogApplication.BlogApplication2.repository.PostRepository;
import com.blogApplication.BlogApplication2.repository.TagRepository;
import com.blogApplication.BlogApplication2.repository.UserRepository;

@Service
public class PostService {
	@Autowired
	private PostRepository postsRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TagRepository tagRepository;
	@Autowired
	private TagService tagService;

	public Set<Post> findPostsByTitleOrContentOrAuthorOrTag(String searchText) {
		String [] searchAll = searchText.split(" ");
		Set<Post> results = new HashSet<>();
		List<User> users = userRepository.findAll();
		List<Tag> tags = tagRepository.findAll();
		for(String searchOne:searchAll) {
			results.addAll(postsRepository.findByTitleContaining(searchOne));
			results.addAll(postsRepository.findByContentContaining(searchOne));
			for(User user:users) {
				if(user.getName().equals(searchOne)) {
					results.addAll(user.getPosts());
				}
			}
			for(Tag tag:tags) {
				if(tag.getName().equals(searchOne)) {
					results.addAll(tag.getPosts());
				}
			}
		}
		return results;
	}

	public List<Post> getAllPostsSortedByPublishDate(String sortBy) {
		List<Post> posts;
		if ("desc".equals(sortBy)) {
			posts = postsRepository.findAll(Sort.by(Sort.Direction.DESC, "publishedAt"));
		}
		else if ("asc".equals(sortBy)) {
			posts = postsRepository.findAll(Sort.by(Sort.Direction.ASC, "publishedAt"));
		} 
		else {
			posts = postsRepository.findAll();
		}
		return posts;
	}

	public Long getPageCount(int limit){
		long postCount = postsRepository.count();
		if (postCount<=limit)
			return null;
		long pageCount=0;
		if(postCount%limit==0) {
			pageCount = postCount/limit;
		}
		else {
			pageCount = (postCount/limit) +1;
		}
		return pageCount;
	}

	public void updatePost(String allTag,Post post) {
		int postId = post.getId();
		Post exitPost = postsRepository.findById(postId).orElse(null);
		if(exitPost != null) {
			String[] tagsName = allTag.split(",");
			for(String s:tagsName) {
				System.out.print(s+" ");
			}
			List<Tag> postTags = exitPost.getTags();
			if(postTags.size()==0) {
				postTags = new ArrayList();
			}
			//			System.out.print(postTags.get(0).getName());
			List<Tag> tags = tagRepository.findAll();
			if(tags.size() == 0) {
				for(String tagName:tagsName) {
					Tag newTag = new Tag();
					newTag.setCreated_At(new Date());
					newTag.setName(tagName.trim());
					newTag.setUpdated_at(new Date());
					tagRepository.save(newTag);
					Tag tag = tagService.findByName(tagName.trim());
					List<Post> tagposts = tag.getPosts();
					if(tagposts==null) {
						tagposts = new ArrayList<>();
					}
					tagposts.add(post);
					postTags.add(tag);
					tagRepository.save(tag);
				}
			}
			else {
				for(String tagName:tagsName) {
					for(Tag tag:tags) {
						if(tag.getName().equals(tagName.trim())) {
							Tag myTag = tagRepository.findById(tag.getId()).get();
							myTag.setUpdated_at(new Date());
							tagRepository.save(myTag);
							Tag tagn = tagRepository.findById(tag.getId()).get();
							List<Post> tagposts = tagn.getPosts();
							if(tagposts==null) {
								tagposts = new ArrayList<>();
							}
							tagposts.add(post);
							postTags.add(tagn);
							tagRepository.save(tagn);
						}

					}
					if(tagService.findByName(tagName.trim())==null) {
						Tag newTag = new Tag();
						newTag.setCreated_At(new Date());
						newTag.setName(tagName.trim());
						newTag.setUpdated_at(new Date());
						tagRepository.save(newTag);
						Tag tagn = tagService.findByName(tagName.trim());
						List<Post> tagposts = tagn.getPosts();
						if(tagposts==null) {
							tagposts = new ArrayList<>();
						}
						tagposts.add(post);
						postTags.add(tagn);
						tagRepository.save(tagn);
					}
				}
			}
			exitPost.setTags(postTags);
			exitPost.setContent(post.getContent());
			exitPost.setExcerpt(post.getExcerpt());
			exitPost.setTitle(post.getTitle());
			exitPost.setUpdated_at(new Date());
			postsRepository.save(exitPost);

		}


	}

	public void addPost(String allTag,Post post) {
		if(post.getPublished_at()==null) {
			post.setPublished_at(new Date());
		}
		List<User> allUser = userRepository.findAll();

		User author = allUser.get(0);
		//		User author = new User();
		//		author.setEmail("shanti2001samanta@gmail.com");
		//		author.setPassword("1234");
		//		author.setName("shanti");

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

		String[] tagsName = allTag.split(",");
		List<Tag> tags = tagRepository.findAll();
		List<Tag> postTags = new ArrayList();
		if(tags.size() == 0) {
			for(String tagName:tagsName) {
				Tag newTag = new Tag();
				newTag.setCreated_At(new Date());
				newTag.setName(tagName.trim());
				newTag.setUpdated_at(new Date());
				tagRepository.save(newTag);
				Tag tag = tagService.findByName(tagName.trim());
				List<Post> tagposts = tag.getPosts();
				if(tagposts==null) {
					tagposts = new ArrayList<>();
				}
				tagposts.add(post);
				postTags.add(tag);
				tagRepository.save(tag);
			}
		}
		else {

			for(String tagName:tagsName) {
				for(Tag tag:tags) {
					if(tag.getName().equals(tagName.trim())) {
						Tag myTag = tagRepository.findById(tag.getId()).get();
						myTag.setUpdated_at(new Date());
						tagRepository.save(myTag);
						Tag tagn = tagRepository.findById(tag.getId()).get();
						List<Post> tagposts = tagn.getPosts();
						if(tagposts==null) {
							tagposts = new ArrayList<>();
						}
						tagposts.add(post);
						postTags.add(tagn);
						tagRepository.save(tagn);
					}

				}
				if(tagService.findByName(tagName.trim())==null) {
					Tag newTag = new Tag();
					newTag.setCreated_At(new Date());
					newTag.setName(tagName.trim());
					newTag.setUpdated_at(new Date());
					tagRepository.save(newTag);
					Tag tagn = tagService.findByName(tagName.trim());
					List<Post> tagposts = tagn.getPosts();
					if(tagposts==null) {
						tagposts = new ArrayList<>();
					}
					tagposts.add(post);
					postTags.add(tagn);
					tagRepository.save(tagn);
				}
			}
		}
		post.setTags(postTags);
		postsRepository.save(post);
	}


	public Set<Post> filterByAuthorAndTags(String[] authorsName,String[] tagsId) {
		
		Set<Post> posts = new HashSet<>();

		List<User> users = userRepository.findAll();
		List<Tag> tags = tagRepository.findAll();
		if(tagsId==null) {
			for(String authorName:authorsName) {
				for(User user : users) {
					if(user.getName().equals(authorName)) {
						posts.addAll(user.getPosts());
					}
				}
			}
		}
		else if(authorsName==null) {
			for(String tagId:tagsId) {
				Tag tag = tagRepository.findById(Integer.parseInt(tagId)).get();
				if(tags.contains(tag)) {
					posts.addAll(tag.getPosts());
				}
			}
		}
		else {
			for(String authorName:authorsName) {
				for(User user : users) {
					if(user.getName().equals(authorName)) {
						List<Post> authorpost = user.getPosts();
						for(Post post:authorpost) {
							List<Tag> postTags = post.getTags();
							for(String tagId:tagsId) {
								Tag tag = tagRepository.findById(Integer.parseInt(tagId)).get();
								if(tags.contains(tag) && postTags.contains(tag)) {
									posts.add(post);
								}
							}
						}
					}
				}
			}
		}
		return posts;
	}







}
