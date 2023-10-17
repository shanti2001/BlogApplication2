package com.blogApplication.BlogApplication2.entity;


import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name="comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String email;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    
    private Date created_at;
    private Date updated_at;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
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
	public Comment(int id, String name, String email, String comment, Post post, Date created_at, Date updated_at) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.comment = comment;
		this.post = post;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	public Comment() {
		
	}

    
}
