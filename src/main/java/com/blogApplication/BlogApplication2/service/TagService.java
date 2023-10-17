package com.blogApplication.BlogApplication2.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogApplication.BlogApplication2.entity.Tag;
import com.blogApplication.BlogApplication2.repository.TagRepository;

@Service
public class TagService {

	@Autowired
	TagRepository tagRepository;
	
	public Tag findByName(String name) {
		return tagRepository.findByName(name);
	}
}
