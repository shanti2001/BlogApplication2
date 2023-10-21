package com.blogApplication.BlogApplication2.configuration;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration

public class SecurityConfig {
	
	@Bean
	public UserDetailsService getUserDetailService() {
		return new UserDetailServiceImpl();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		
		daoAuthenticationProvider.setUserDetailsService(getUserDetailService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return daoAuthenticationProvider;
	}
	
	@Bean
	public SecurityFilterChain filterChani(HttpSecurity http) throws Exception {
		http
        .authorizeHttpRequests(authorizeRequests ->
            authorizeRequests
                .requestMatchers("/user/**").authenticated() 
                .requestMatchers("/newpost").authenticated() 
                .anyRequest().permitAll())
                .formLogin(login ->login.loginPage("/login")
                		.loginProcessingUrl("/user/userpage")
                		.defaultSuccessUrl("/user/userpage")
                        .permitAll()
                		)
                .logout(logout -> logout.permitAll());
 
    return http.build();
	}

}
