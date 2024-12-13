package com.bookrental.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntityDelete {

	@Column(name = "deleted")
	private Boolean deleted = Boolean.FALSE;
}
