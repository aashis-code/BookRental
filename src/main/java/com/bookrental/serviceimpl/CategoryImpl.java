package com.bookrental.serviceimpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.bookrental.exceptions.ResourceAlreadyExist;
import com.bookrental.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import com.bookrental.dto.CategoryDto;
import com.bookrental.dto.FilterRequest;
import com.bookrental.dto.PaginatedResponse;
import com.bookrental.helper.CoustomBeanUtils;
import com.bookrental.helper.CustomPagination;
import com.bookrental.model.Book;
import com.bookrental.model.Category;
import com.bookrental.repository.CategoryRepo;
import com.bookrental.service.CategoryService;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        category = new Category();
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
	public PaginatedResponse getPaginatedCategoryList(FilterRequest filterRequest) {
		Map<String, Object> object = CustomPagination.getPaginatedObject(filterRequest);
		Page<Category> response = categoryRepo.findByDeleted(object.get("keyword").toString(), (LocalDate)object.get("startDate"), (LocalDate)object.get("endDate"), Boolean.FALSE, (Pageable) object.get("pageable"));
		return PaginatedResponse.builder().content(response.getContent())
				.totalElements(response.getTotalElements()).currentPageIndex(response.getNumber())
				.numberOfElements(response.getNumberOfElements()).totalPages(response.getTotalPages()).build();

	}

    @Override
    @Transactional
    public void deleteCategoryById(Integer categoryId) {
        if (categoryId < 1) {
            throw new ResourceNotFoundException("Invalid Category Id.", null);
        }
         int result = categoryRepo.deleteCategoryById(categoryId);
         if(result<1) {
        	 throw new ResourceNotFoundException("CategoryId", String.valueOf(categoryId));
         }
    }

}
