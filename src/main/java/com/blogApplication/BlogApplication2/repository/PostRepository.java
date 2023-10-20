package com.blogApplication.BlogApplication2.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blogApplication.BlogApplication2.entity.Post;


public interface PostRepository extends JpaRepository<Post, Integer>{

    List<Post> findByTitleContaining(String searchText);
    List<Post> findByContentContaining(String searchText);
    
    @Query("Select p.publishedAt from Post p")
    List<Date> findPublishDate();
}
