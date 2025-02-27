package com.bookrental.model;

import java.util.List;

import com.bookrental.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "category", uniqueConstraints = { @UniqueConstraint(name= "uk_category_name", columnNames = { "name" }) })
public class Category extends Auditable {

	@Id
	@SequenceGenerator(name = "category_seq_gen", allocationSize = 1, sequenceName = "category_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq_gen")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;


}
