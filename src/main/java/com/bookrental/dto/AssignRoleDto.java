package com.bookrental.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignRoleDto {

	private Integer memberId;

	private List<String> roles;

}
