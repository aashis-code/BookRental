package com.bookrental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookrental.dto.CategoryDto;
import com.bookrental.helper.ResponseObject;
import com.bookrental.model.Category;
import com.bookrental.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/api/category")
public class CategoryController extends BaseController{

	private final CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService=categoryService;
	}

	@PostMapping("/")
	public ResponseObject createCategory(@RequestBody @Valid CategoryDto categoryDto) {
		return getSuccessResponse("Success !!", categoryService.categoryCUD(categoryDto));

	}
	
	@GetMapping("/{categoryId}")
	public ResponseObject getCategoryById(@PathVariable Integer categoryId) {
		return getSuccessResponse("Successfull !!", categoryService.getCategoryById(categoryId));
	}

}
