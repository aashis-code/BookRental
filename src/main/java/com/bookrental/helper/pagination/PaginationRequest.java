package com.bookrental.helper.pagination;

import com.bookrental.helper.daterange.DateRangeFilter;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationRequest extends DateRangeFilter {

    @Min(value = 1, message = "Page cannot be less than 1.")
    private int page = 0;

    @Min(value = 1, message = "Request per page cannot be less than 1.")
    private int size = 1;

    String searchFeild;

    Boolean isDeleted = Boolean.FALSE;

    public int getPage() {
        return page == 0 ? 0 : page - 1;
    }

    public Pageable getPageable() {
        return PageRequest.of(getPage(), getSize());
    }
}
