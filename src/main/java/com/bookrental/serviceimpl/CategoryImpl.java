package com.bookrental.serviceimpl;

import java.util.List;
import java.util.Optional;

import com.bookrental.exceptions.ResourceAlreadyExist;
import com.bookrental.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import com.bookrental.dto.CategoryDto;
import com.bookrental.helper.CoustomBeanUtils;
import com.bookrental.model.Category;
import com.bookrental.repository.CategoryRepo;
import com.bookrental.service.CategoryService;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryImpl implements CategoryService {

    private final CategoryRepo categoryRepo;

    @Override
    public boolean categorySaveAndUpdate(CategoryDto categoryDto) {
        Category category = null;
        if (categoryDto.getId() != null) {
            category = categoryRepo.findById(categoryDto.getId()).orElseThrow(() -> new RuntimeException("Category id not found"));
        } else {
        	String name = categoryDto.getName().trim();
        	Optional<Category> byName = categoryRepo.findByName(name);
        	if(byName.isPresent()) {
        		throw new ResourceAlreadyExist("Category Name", name);
        	}
        }
        
        CoustomBeanUtils.copyNonNullProperties(categoryDto, category);
        categoryRepo.save(category);
        return true;
    }

    @Override
    public Category getCategoryById(Integer categoryId) {
        if (categoryId < 1) {
            throw new ResourceNotFoundException("Please, Enter valid Category Id.", null);
        }
        return categoryRepo.findByIdAndDeleted(categoryId, Boolean.FALSE).orElseThrow(() -> new ResourceNotFoundException("CategoryId", String.valueOf(categoryId)));
    }

    @Override
    public List<Category> getAllCategory() {
         return categoryRepo.findAllCategoryByIsDeleted(Boolean.FALSE);
    }

    @Override
    @Transactional
    public boolean deleteCategoryById(Integer categoryId) {
        if (categoryId < 1) {
            throw new ResourceNotFoundException("Invalid Category Id.", null);
        }
        categoryRepo.findByIdAndDeleted(categoryId, Boolean.FALSE).orElseThrow(() -> new ResourceNotFoundException("categoryId", String.valueOf(categoryId)));
        categoryRepo.deleteCategoryById(categoryId);
        return true;
    }

}