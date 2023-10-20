package com.blogApplication.BlogApplication2.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.blogApplication.BlogApplication2.entity.User;
import com.blogApplication.BlogApplication2.repository.UserRepository;

public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.getUserByUserName(username);
		if(user==null) {
			throw new UsernameNotFoundException("Could not found User !");
		}
		CustomUserDetails customUserDetails = new CustomUserDetails(user);
		
		return customUserDetails;
	}

}