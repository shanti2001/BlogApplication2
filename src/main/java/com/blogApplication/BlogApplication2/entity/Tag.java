package com.blogApplication.BlogApplication2.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;


@Entity
@Table(name="tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Post> posts;
    
    private Date created_At;
    
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

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public Date getCreated_At() {
		return created_At;
	}

	public void setCreated_At(Date created_At) {
		this.created_At = created_At;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public Tag(int id, String name, List<Post> posts, Date created_At, Date updated_at) {
		super();
		this.id = id;
		this.name = name;
		this.posts = posts;
		this.created_At = created_At;
		this.updated_at = updated_at;
	}

	public Tag() {
		
	}
    
    

    
}
