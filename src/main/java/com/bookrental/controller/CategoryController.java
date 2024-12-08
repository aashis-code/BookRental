package com.bookrental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookrental.dto.CategoryDto;
import com.bookrental.service.CategoryService;

@RestController
@RequestMapping(path = "/api/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping(value = "/create")
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
		CategoryDto savedCategory = categoryService.addCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(savedCategory, HttpStatus.CREATED);
	}

}
