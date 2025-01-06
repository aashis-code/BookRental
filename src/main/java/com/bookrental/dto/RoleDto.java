package com.bookrental.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDto {
	
	private Integer id;
	
	@Size(min = 3, max = 20, message = "Role name must be 3 to 20 characters long.")
	private String name;
	
	private String description;
	
	private Boolean toDelete;

}
