package com.bookrental.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
@Table(name = "member", uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }) })
public class Member implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "member_gen", allocationSize = 1, sequenceName = "member_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
	private Integer id;

	private String email;

	private String name;

	private String mobileNo;

	private String address;
	
	private String password;

	@OneToMany(mappedBy = "member")
	private List<BookTransaction> bookTransactions;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

}
