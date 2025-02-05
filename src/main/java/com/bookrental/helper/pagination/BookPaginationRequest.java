package com.bookrental.helper.pagination;

import com.bookrental.helper.OrderBy;
import com.bookrental.helper.RentType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookPaginationRequest extends PaginationRequest{

    Integer bookId;

    Integer authorId;

    Integer categoryId;

    Integer memberId;

    @Enumerated(EnumType.STRING)
    private RentType rentStatus=RentType.RENT;

    @Enumerated(EnumType.STRING)
    private OrderBy orderBy = OrderBy.NAME;

}
