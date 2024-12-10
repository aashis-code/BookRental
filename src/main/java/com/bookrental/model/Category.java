package com.bookrental.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="category")
public class Category {

	@Id
	@SequenceGenerator(name = "category_gen", allocationSize = 1, sequenceName = "category_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
	private Integer id;

	private String name;

	private String description;

	@OneToMany(mappedBy = "category")
	private List<Book> books;

	
}
