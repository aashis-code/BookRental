package com.bookrental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookrental.dto.CategoryDto;
import com.bookrental.helper.ResponseObject;
import com.bookrental.service.CategoryService;

@RestController
@RequestMapping(path = "/api/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/create")
	public ResponseObject createCategory(@RequestBody CategoryDto categoryDto) {

		return ResponseObject.builder().status(true).message("Successfully saved.")
				.data(categoryService.addCategory(categoryDto)).build();

	}

}
