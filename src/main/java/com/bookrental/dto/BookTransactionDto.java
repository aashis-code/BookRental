package com.bookrental.dto;

import org.springframework.beans.factory.annotation.Value;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookTransactionDto {

	private Integer id;

	@NotNull(message = "Enter member Id.")
	@Min(value = 1, message = "Enter valid member Id.")
	private Integer memberId;

	@NotNull(message = "Enter book Id.")
	@Min(value = 1, message = "Enter valid book Id.")
	private Integer bookId;

	@Size(min = 1, message = "Rent duration must be at least 1 day long.")
	private Integer rentDuration;
	
	@Value("${false}")
	private Boolean toReturn;

}
