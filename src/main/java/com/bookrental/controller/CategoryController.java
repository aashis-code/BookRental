package com.bookrental.controller;

import com.bookrental.dto.CategoryDto;
import com.bookrental.helper.ResponseObject;
import com.bookrental.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/category")
public class CategoryController extends BaseController{

	private final CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService=categoryService;
	}

	@PostMapping("/")
	public ResponseObject createCategory(@RequestBody @Valid CategoryDto categoryDto) {
		return getSuccessResponse("Success !!", categoryService.categorySaveAndUpdate(categoryDto));
	}
	
	@GetMapping("/{categoryId}")
	public ResponseObject getCategoryById(@PathVariable Integer categoryId) {
		return getSuccessResponse("Successfull !!", categoryService.getCategoryById(categoryId));
	}

	@GetMapping("/")
	public ResponseObject getAllCategories() {
		return getSuccessResponse("Successfully fetched all categories !!", categoryService.getAllCategory());
	}

	@DeleteMapping("/{categoryId}")
	public ResponseObject deleteRoleId(@PathVariable Integer categoryId) {
		return getSuccessResponse("Successfully deleted member !!", categoryService.deleteCategoryById(categoryId));
	}

}
