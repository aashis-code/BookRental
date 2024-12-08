package com.bookrental.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bookrental.modelmapper.AuthorModelMapper;
import com.bookrental.modelmapper.BookModelMapper;
import com.bookrental.modelmapper.CategoryModelMapper;

@Configuration
public class Application {

	
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
	
}
