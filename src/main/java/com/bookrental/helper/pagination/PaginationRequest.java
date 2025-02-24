package com.bookrental.helper.pagination;

import com.bookrental.helper.SortMethod;
import com.bookrental.helper.daterange.DateRangeFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationRequest extends DateRangeFilter {

    @Min(value = 1, message = "Page cannot be less than 1.")
    private int page = 0;

    @Min(value = 1, message = "Request per page cannot be less than 1.")
    private int size = 1;

    @Schema(example = "id")
    private String sortBy="id";

    @Enumerated(EnumType.STRING)
    private SortMethod sortDirection=SortMethod.DESC;

    @Schema(example = "null")
    private String searchField;

    Boolean isDeleted = Boolean.FALSE;

    public int getPage() {
        return page == 0 ? 0 : page - 1;
    }

//    public Pageable getPageable() {
//        return PageRequest.of(getPage(), getSize());
//    }

}
