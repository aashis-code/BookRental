package com.bookrental.model;

import java.util.List;

import com.bookrental.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "author", uniqueConstraints = { @UniqueConstraint(name="uk_author_email", columnNames = { "email"}),
                                              @UniqueConstraint(name="uk_author_mobile_number", columnNames = { "mobile_number"})})
public class Author extends Auditable {

	@Id
	@SequenceGenerator(name = "author_seq_gen", allocationSize = 1, sequenceName = "author_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_seq_gen")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "mobile_number")
	private String mobileNumber;

}
