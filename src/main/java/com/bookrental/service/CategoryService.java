package com.bookrental.service;

import org.springframework.stereotype.Service;

import com.bookrental.dto.CategoryDto;


@Service
public interface CategoryService {
	
	CategoryDto addCategory(CategoryDto categoryDto);

}
