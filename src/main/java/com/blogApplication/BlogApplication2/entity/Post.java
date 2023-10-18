package com.blogApplication.BlogApplication2.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="posts")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String title;
	private String excerpt;
	@Column(length = 5000)
	private String content;

	@ManyToOne
	@JoinColumn(name = "author")
	private User author;

	private Date publishedAt;

	private boolean is_published;

	@ManyToMany
	@JoinTable(
			name = "post_tags",
			joinColumns = @JoinColumn(name = "post_id"),
			inverseJoinColumns = @JoinColumn(name = "tag_id")
			)
	private List<Tag> tags;

	@OneToMany(mappedBy = "post")
	private List<Comment> comments;

	private Date created_at;

	private Date updated_at;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getExcerpt() {
		return excerpt;
	}

	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Date getPublished_at() {
		return publishedAt;
	}

	public void setPublished_at(Date published_at) {
		this.publishedAt = published_at;
	}

	public boolean isIs_published() {
		return is_published;
	}

	public void setIs_published(boolean is_published) {
		this.is_published = is_published;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public Post(int id, String title, String excerpt, String content, User author, Date published_at,
			boolean is_published, List<Tag> tags, List<Comment> comments, Date created_at, Date updated_at) {

		this.id = id;
		this.title = title;
		this.excerpt = excerpt;
		this.content = content;
		this.author = author;
		this.publishedAt = published_at;
		this.is_published = is_published;
		this.tags = tags;
		this.comments = comments;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	public Post() {

	}


}

