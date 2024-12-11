package com.bookrental.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bookrental.dto.CategoryDto;
import com.bookrental.model.Category;


@Service
public interface CategoryService {
	
	boolean categoryCUD(CategoryDto categoryDto);
	
	Category getCategoryById(Integer categoryId);
	
	List<Category> getAllCategory();

}
