package com.bookrental.dto;

import java.util.Date;
import java.util.Set;

public class BookAddRequest {

	private int id;

	private String name;

	private int number_of_pages;

	private int isbn;

	private double rating;

	private int stock_count;

	private Date published_date;

	private String photo;

	private Set<Integer> author_id;

	private int category_id;

	public BookAddRequest() {
		super();
	}

	public BookAddRequest(int id, String name, int number_of_pages, int isbn, double rating, int stock_count,
			Date published_date, String photo, Set<Integer> author_id, int category_id) {
		super();
		this.id = id;
		this.name = name;
		this.number_of_pages = number_of_pages;
		this.isbn = isbn;
		this.rating = rating;
		this.stock_count = stock_count;
		this.published_date = published_date;
		this.photo = photo;
		this.author_id = author_id;
		this.category_id = category_id;
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

	public Set<Integer> getAuthor_id() {
		return author_id;
	}

	public void setAuthor_id(Set<Integer> author_id) {
		this.author_id = author_id;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

}
