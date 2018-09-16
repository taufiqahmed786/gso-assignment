package com.todolist.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	// In Memomory user managerment
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(org.springframework.security
				.crypto.password.NoOpPasswordEncoder.getInstance())
				.withUser("Ahmad").password("ahmad")
				.roles("USER" , "ADMIN")
				.and()
				.withUser("Bilal").password("bilal")
				.roles("USER");
	}

	// API Authorizations
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().authorizeRequests()
		.antMatchers("/manage/**").permitAll()
		.antMatchers("/todo/items/all").hasRole("ADMIN")
		.antMatchers("/todo/**").hasRole("USER")
		.and().csrf().disable()
		.headers().frameOptions().disable();
	}
}
