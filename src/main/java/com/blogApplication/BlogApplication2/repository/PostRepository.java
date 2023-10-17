package com.blogApplication.BlogApplication2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.blogApplication.BlogApplication2.entity.Post;


public interface PostRepository extends JpaRepository<Post, Integer>{

    List<Post> findByTitleContaining(String searchText);
    List<Post> findByContentContaining(String searchText);
    

}
