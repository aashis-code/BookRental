package com.bookrental.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDto {
	
	private Integer id;
	
	private String name;
	
	private String description;
	
	private Boolean toDelete;

}