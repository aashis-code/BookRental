package com.bookrental.helper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.bookrental.dto.FilterRequest;
import com.bookrental.exceptions.ResourceNotFoundException;

public class CustomPagination {

	private CustomPagination(){
		throw new IllegalStateException("Utility class");
	}


	public static Map<String, Object> getPaginatedObject(FilterRequest filterRequest) {

		Integer pageNumber = Optional.ofNullable(filterRequest.getPageNumber()).orElse(0);
		Integer pageSize = Optional.ofNullable(filterRequest.getPageSize()).orElse(5);
		String keyword = Optional.ofNullable(filterRequest.getKeyword()).orElse("");
		String searchField = Optional.ofNullable(filterRequest.getSearchField()).orElse("name");
		Integer bookId = filterRequest.getBookId();
		Integer memberId = filterRequest.getMemberId();
		String rentStatus = filterRequest.getRentStatus() != null ? filterRequest.getRentStatus().toString() : null;
		
		LocalDateTime startDate;
		if (filterRequest.getStartDate() != null) {
			if(!filterRequest.getStartDate().isBefore(LocalDate.now())
					|| filterRequest.getStartDate().isEqual(LocalDate.now())) {
				startDate = LocalDateTime.of(filterRequest.getStartDate(), LocalTime.MIDNIGHT);
			} else{
				throw new ResourceNotFoundException("Invalid start date.", null);
			}
		} else {
			startDate = LocalDateTime.of(2001, 1, 1, 0, 0, 0);
		}

		LocalDateTime endDate;
		if (filterRequest.getEndDate() != null) {
			if(!filterRequest.getEndDate().isBefore(LocalDate.now())
					|| filterRequest.getStartDate().isEqual(LocalDate.now())) {
				endDate = LocalDateTime.of(filterRequest.getEndDate(), LocalTime.now());
			} else{
				throw new ResourceNotFoundException("Invalid end date.", null);
			}
		} else {
			endDate = LocalDateTime.now();
		}
			

		String orderBy = Optional.ofNullable(filterRequest.getOrderBy()).orElse("id");
		String sortDir = Optional.ofNullable(filterRequest.getSortDir()).orElse(SortMethod.ASC.toString());
		Sort.Direction direction = Sort.Direction.fromOptionalString(sortDir).orElse(Sort.Direction.ASC);

		Sort sort = Sort.by(direction, orderBy);

		Pageable pageAble = PageRequest.of(pageNumber, pageSize, sort);

		Map<String, Object> hasMap = new HashMap<>();
		hasMap.put("pageable", pageAble);
		hasMap.put("keyword", keyword);
		hasMap.put("startDate", startDate);
		hasMap.put("endDate", endDate);
		hasMap.put("searchField", searchField);
		hasMap.put("bookId", bookId);
		hasMap.put("memberId", memberId);
        hasMap.put("rentStatus", rentStatus);
		return hasMap;

	}

}
