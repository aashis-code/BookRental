package com.bookrental.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bookrental.exceptions.ResourceNotFoundException;
import com.bookrental.modelmapper.AuthorModelMapper;
import com.bookrental.modelmapper.BookModelMapper;
import com.bookrental.modelmapper.CategoryModelMapper;
import com.bookrental.modelmapper.MemberModelMapper;
import com.bookrental.repository.MemberRepo;

@Configuration
public class Application {

	private final MemberRepo memberRepo;

	public Application(MemberRepo memberRepo) {
		this.memberRepo = memberRepo;
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public AuthorModelMapper authorModelMapper() {
		return new AuthorModelMapper();
	}

	@Bean
	public CategoryModelMapper categoryModelMapper() {
		return new CategoryModelMapper();
	}

	@Bean
	public BookModelMapper bookModelMapper() {
		return new BookModelMapper();
	}
	
//	BEAN FOR JWT SECURITY



	@Bean
	public MemberModelMapper memberModelMapper() {
		return new MemberModelMapper();
	}







}
