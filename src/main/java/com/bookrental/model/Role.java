package com.bookrental.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="role")
public class Role {
	
	@Id
	@SequenceGenerator(name = "role_gen", allocationSize = 1, sequenceName = "role_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
	private Integer id;
	
	private String name;
	
	private String description;
	
	@ManyToMany(mappedBy = "roles")
	List<Member> members;

}
