package com.bookrental.helper.daterange;

import com.bookrental.exceptions.AppException;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DateRangeFilter {

    private LocalDate fromDate;
    private LocalDate toDate;

    public LocalDate getToDate() {
        return toDate != null ? toDate.plusDays(1) : null;
    }
}
