package com.bookrental.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;

	private int number_of_pages;

	private int isbn;

	private double rating;

	private int stock_count;

	private Date published_date;

	private String photo;

	@ManyToMany(mappedBy = "books", cascade = CascadeType.ALL)
	private List<Author> authors;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@OneToMany(mappedBy = "book")
	private List<BookTransaction> book_transactions;

	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Book(int id, String name, int number_of_pages, int isbn, double rating, int stock_count, Date published_date,
			String photo, List<Author> authors, Category category, List<BookTransaction> book_transactions) {
		super();
		this.id = id;
		this.name = name;
		this.number_of_pages = number_of_pages;
		this.isbn = isbn;
		this.rating = rating;
		this.stock_count = stock_count;
		this.published_date = published_date;
		this.photo = photo;
		this.authors = authors;
		this.category = category;
		this.book_transactions = book_transactions;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber_of_pages() {
		return number_of_pages;
	}

	public void setNumber_of_pages(int number_of_pages) {
		this.number_of_pages = number_of_pages;
	}

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getStock_count() {
		return stock_count;
	}

	public void setStock_count(int stock_count) {
		this.stock_count = stock_count;
	}

	public Date getPublished_date() {
		return published_date;
	}

	public void setPublished_date(Date published_date) {
		this.published_date = published_date;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<BookTransaction> getBook_transactions() {
		return book_transactions;
	}

	public void setBook_transactions(List<BookTransaction> book_transactions) {
		this.book_transactions = book_transactions;
	}

}
