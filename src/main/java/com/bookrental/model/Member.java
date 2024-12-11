package com.bookrental.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member", uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }) })
public class Member {

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
    
	@ManyToMany
	@JoinTable(name="member_role",
	           joinColumns = @JoinColumn(name="memberId", foreignKey = @ForeignKey(name="FK_member_role_member")),
	           inverseJoinColumns = @JoinColumn(name="roleId", foreignKey = @ForeignKey(name="FK_member_role_role")))
	private List<Role> roles;
}
