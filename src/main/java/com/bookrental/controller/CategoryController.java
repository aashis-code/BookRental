package com.bookrental.controller;

import com.bookrental.dto.CategoryDto;
import com.bookrental.helper.ResponseObject;
import com.bookrental.helper.constants.MessageConstants;
import com.bookrental.helper.constants.ModuleNameConstants;
import com.bookrental.helper.pagination.PaginationRequest;
import com.bookrental.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/category")
@Tag(name = "Category", description = "Endpoints for managing Category related activities.")
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_LIBRARIAN')")
public class CategoryController extends BaseController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("")
    public ResponseObject createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_UPDATE, ModuleNameConstants.CATEGORY_CONTROLLER), categoryService.categorySaveAndUpdate(categoryDto));
    }

    @GetMapping("/{id}")
    public ResponseObject getCategoryById(@PathVariable Integer id) {
        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_GET, ModuleNameConstants.CATEGORY_CONTROLLER), categoryService.getCategoryById(id));
    }

    @GetMapping("/")
    public ResponseObject getAllCategories() {
        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_GET, ModuleNameConstants.CATEGORY_CONTROLLER), categoryService.getAllCategory());
    }

    @PostMapping("/paginated")
    public ResponseObject getPaginatedCategoryList(@RequestBody PaginationRequest paginationRequest) {
        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_GET, ModuleNameConstants.CATEGORY_CONTROLLER), categoryService.getPaginatedCategoryList(paginationRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseObject deleteRoleId(@PathVariable Integer id) {
        categoryService.deleteCategoryById(id);
        return getSuccessResponse(customMessageSource.get(MessageConstants.CRUD_DELETE, ModuleNameConstants.CATEGORY_CONTROLLER), true);

    }

}
