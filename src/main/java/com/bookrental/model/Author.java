package com.bookrental.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="author")
public class Author {

	@Id
	@SequenceGenerator(name= "author_gen", allocationSize = 1, sequenceName = "author_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_seq")
	private int id;

	private String name;

	private String email;

	private String mobileNumber;

	@ManyToMany(mappedBy = "authors")
	@JsonBackReference
	private List<Book> books;


}
