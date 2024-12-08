package com.bookrental.dto;

public class AuthorDto {
	
	private int id;
	
	private String name;
	
	private String email;
	
	private String mobile_number;

	public AuthorDto() {
		super();
	}

	public AuthorDto(int id, String name, String email, String mobile_number) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.mobile_number = mobile_number;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}
	
	
}
