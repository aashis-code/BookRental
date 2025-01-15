package com.bookrental.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseEntityDelete {

	@Column(name = "deleted")
	private Boolean deleted = Boolean.FALSE;
}
