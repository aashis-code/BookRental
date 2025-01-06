package com.bookrental.model;

import java.time.LocalDate;
import java.util.List;
import com.bookrental.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book", uniqueConstraints = { @UniqueConstraint(name = "uk_book_isbn", columnNames = { "isbn" }) })
public class Book extends Auditable {

	@Id
	@SequenceGenerator(name = "book_seq_gen", allocationSize = 1, sequenceName = "book_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq_gen")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "number_of_pages")
	private Integer numberOfPages;

	@Column(name = "isbn")
	private String isbn;

	@Column(name = "rating")
	private Double rating;

	@Column(name = "stock_count")
	private Integer stockCount;

	@Column(name = "published_date")
	private LocalDate publishedDate;

	@Column(name = "photo")
	private String photo;

	@ManyToMany
	@JoinTable(name = "book_author", joinColumns = @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "FK_book_author_book")), inverseJoinColumns = @JoinColumn(name = "author_id", foreignKey = @ForeignKey(name = "FK_book_author_author")))
	private List<Author> authors;

	@ManyToOne
	@JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "FK_book_category"))
	private Category category;
	

}
