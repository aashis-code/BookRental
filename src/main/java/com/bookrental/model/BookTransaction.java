package com.bookrental.model;

import java.util.Date;

import com.bookrental.helper.RENT_TYPE;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class BookTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String code;

	private Date from_date;

	private Date to_date;

	private RENT_TYPE rent_status;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;

	public BookTransaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BookTransaction(int id, String code, Date from_date, Date to_date, RENT_TYPE rent_status, Member member,
			Book book) {
		super();
		this.id = id;
		this.code = code;
		this.from_date = from_date;
		this.to_date = to_date;
		this.rent_status = rent_status;
		this.member = member;
		this.book = book;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getFrom_date() {
		return from_date;
	}

	public void setFrom_date(Date from_date) {
		this.from_date = from_date;
	}

	public Date getTo_date() {
		return to_date;
	}

	public void setTo_date(Date to_date) {
		this.to_date = to_date;
	}

	public RENT_TYPE getRent_status() {
		return rent_status;
	}

	public void setRent_status(RENT_TYPE rent_status) {
		this.rent_status = rent_status;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

}
