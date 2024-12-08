package com.bookrental.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String email;

	private String name;

	private int mobile_no;

	private String address;

	@OneToMany(mappedBy = "member")
	private List<BookTransaction> book_transactions;

	public Member() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Member(int id, String email, String name, int mobile_no, String address,
			List<BookTransaction> book_transactions) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.mobile_no = mobile_no;
		this.address = address;
		this.book_transactions = book_transactions;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMobile_no() {
		return mobile_no;
	}

	public void setMobile_no(int mobile_no) {
		this.mobile_no = mobile_no;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<BookTransaction> getBook_transactions() {
		return book_transactions;
	}

	public void setBook_transactions(List<BookTransaction> book_transactions) {
		this.book_transactions = book_transactions;
	}

}
