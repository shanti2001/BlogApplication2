package com.blogApplication.BlogApplication2.configuration;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.blogApplication.BlogApplication2.entity.User;
import com.blogApplication.BlogApplication2.repository.UserRepository;

public class CustomUserDetails implements UserDetails{

//	@Autowired
//	private UserRepository userRepository;
//	
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Users user = userRepository.getUserByUserName(username);
//		if(user==null) {
//			throw new UsernameNotFoundException("Could not found User !");
//		}
//		UserDetails userDetails = Users
//	            .withUsername(user.getEmail())
//	            .password(user.getPassword())
//	            .roles("USER") // Add user roles here
//	            .build();
//	}
	
	private User user;
	
	public CustomUserDetails(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		SimpleGrantedAuthority simpleGrantedAuthority =  new SimpleGrantedAuthority(user.getName());
		return List.of(simpleGrantedAuthority);
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}

}
