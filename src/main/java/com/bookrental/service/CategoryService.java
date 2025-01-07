package com.bookrental.service;

import com.bookrental.dto.CategoryDto;
import com.bookrental.dto.PaginatedResponse;
import com.bookrental.helper.pagination.PaginationRequest;
import com.bookrental.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface CategoryService {

    boolean categorySaveAndUpdate(CategoryDto categoryDto);

    Category getCategoryById(Integer categoryId);

    List<Category> getAllCategory();

    PaginatedResponse getPaginatedCategoryList(PaginationRequest paginationRequest);

    void deleteCategoryById(Integer bookId);


}
