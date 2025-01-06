package com.bookrental.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bookrental.dto.CategoryDto;
import com.bookrental.dto.FilterRequest;
import com.bookrental.dto.PaginatedResponse;
import com.bookrental.model.Author;
import com.bookrental.model.Category;


@Service
public interface CategoryService {
	
	boolean categorySaveAndUpdate(CategoryDto categoryDto);
	
	Category getCategoryById(Integer categoryId);
	
	List<Category> getAllCategory();
	
	PaginatedResponse getPaginatedCategoryList(FilterRequest filterRequest);
	
	void deleteCategoryById(Integer bookId);
	
	

}
