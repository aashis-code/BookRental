package com.bookrental.helper.pagination;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class CustomPageable  {


    public static Pageable getPageable(PaginationRequest request) {

        Sort sort;
        if (request.getSortDirection().equals(Sort.Direction.DESC)) {
            sort = Sort.by(Sort.Direction.DESC, request.getSortBy());
        } else {
            sort = Sort.by(Sort.Direction.ASC, request.getSortBy());
        }
        return PageRequest.of(request.getPage(), request.getSize(), sort);
    }

}
