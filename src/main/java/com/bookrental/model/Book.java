package com.bookrental.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Table(name="book", uniqueConstraints = {@UniqueConstraint(columnNames = {"isbn"})})
public class Book {

	@Id
	@SequenceGenerator(name= "book_gen", allocationSize = 1, sequenceName = "book_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
	private Integer id;

	private String name;

	private Integer numberOfPages;

	private String isbn;

	private Double rating;

	private Integer stockCount;

	private Date publishedDate;

	private String photo;

	@ManyToMany
	@JoinTable(
			name="book_author",
			joinColumns = @JoinColumn(name="book_id", foreignKey = @ForeignKey(name="FK_book_author_book")),
			inverseJoinColumns = @JoinColumn(name="author_id", foreignKey = @ForeignKey(name = "FK_book_author_author")))
	private List<Author> authors;

	@ManyToOne
	@JoinColumn(name = "categoryId", foreignKey = @ForeignKey(name="FK_book_category"))
	private Category category;

	@OneToMany(mappedBy = "book")
	private List<BookTransaction> bookTransactions;

	
}
