package com.blogApplication.BlogApplication2;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.blogApplication.BlogApplication2.repository.TagRepository;
import com.blogApplication.BlogApplication2.repository.UserRepository;
import com.blogApplication.BlogApplication2.entity.*;

@SpringBootApplication
public class BlogApplication2Application {
	
//	@Autowired
//	TagsRepository tagsRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogApplication2Application.class, args);
	}
//	@Bean
//	public CommandLineRunner commandLineRunner() {
//		return runner ->{
//			saveTags();
//		};
//	}
//	private void saveTags() {
//		Tags u = new Tags();
//		u.setName("java");
//		if(u.getCreatedAt()==null) {
//			u.setCreatedAt(new Date());
//		}
//		if(u.getUpdatedAt()==null) {
//			u.setUpdatedAt(new Date());
//		}
//		
//		
//		
//		tagsRepository.save(u);
//	}
}
