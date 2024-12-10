package com.bookrental.model;

import java.util.List;

import jakarta.persistence.Entity;
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
@Table(name = "member", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class Member {

	@Id
	@SequenceGenerator(name = "member_gen", allocationSize = 1, sequenceName = "member_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
	private Integer id;

	private String email;

	private String name;

	private String mobileNo;

	private String address;

	@OneToMany(mappedBy = "member")
	private List<BookTransaction> bookTransactions;

	
}
